package ca.ulaval.glo4003.ultaxi.domain.messaging.messagingtask;

import ca.ulaval.glo4003.ultaxi.domain.messaging.sms.Sms;
import ca.ulaval.glo4003.ultaxi.domain.messaging.sms.SmsSender;
import ca.ulaval.glo4003.ultaxi.domain.messaging.sms.exception.SmsSendingFailureException;
import ca.ulaval.glo4003.ultaxi.domain.messaging.sms.exception.UnrecoverableSmsSendingFailureException;
import ca.ulaval.glo4003.ultaxi.domain.transportrequest.TransportRequest;
import ca.ulaval.glo4003.ultaxi.domain.transportrequest.TransportRequestRepository;
import ca.ulaval.glo4003.ultaxi.domain.transportrequest.TransportRequestStatus;
import ca.ulaval.glo4003.ultaxi.domain.user.PhoneNumber;
import ca.ulaval.glo4003.ultaxi.domain.user.User;
import ca.ulaval.glo4003.ultaxi.domain.user.UserRepository;
import net.jodah.failsafe.Failsafe;
import net.jodah.failsafe.RetryPolicy;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class SendDriverHasArrivedSmsTask implements MessagingTask {

    private static final String SMS_BODY_CONTENT = "A driver has answered your request and is waiting for you at the " +
        "scheduled point.";
    private static final int TIME_UNITS_BETWEEN_RETRY_ATTEMPT = 60;

    private final String sourcePhoneNumber;
    private final SmsSender smsSender;
    private final UserRepository userRepository;
    private final TransportRequestRepository transportRequestRepository;
    private final String transportRequestId;

    public SendDriverHasArrivedSmsTask(String sourcePhoneNumber, SmsSender smsSender, String transportRequestId,
        TransportRequestRepository transportRequestRepository, UserRepository userRepository) {
        this.sourcePhoneNumber = sourcePhoneNumber;
        this.smsSender = smsSender;
        this.transportRequestId = transportRequestId;
        this.transportRequestRepository = transportRequestRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void run() {
        RetryPolicy retryPolicy = createRetryPolicy();
        Failsafe
            .with(retryPolicy)
            .run(this::execute);
    }

    private RetryPolicy createRetryPolicy() {
        return new RetryPolicy()
            .retryOn(SmsSendingFailureException.class)
            .abortOn(UnrecoverableSmsSendingFailureException.class)
            .abortIf(this::hasCourseStarted)
            .withDelay(TIME_UNITS_BETWEEN_RETRY_ATTEMPT, TimeUnit.SECONDS);
    }

    private boolean hasCourseStarted(Object object) {
        TransportRequest transportRequest = transportRequestRepository.findById(transportRequestId);
        return transportRequest != null
            && transportRequest.getTransportRequestStatus() == TransportRequestStatus.STARTED;
    }

    public void execute() {
        String destinationPhoneNumber = Optional
            .ofNullable(transportRequestRepository.findById(transportRequestId))
            .map(transportRequest -> userRepository.findByUsername(transportRequest.getClientUsername()))
            .map(User::getPhoneNumber)
            .map(PhoneNumber::getNumber)
            .orElseThrow(() -> new UnrecoverableSmsSendingFailureException("No transport request/client could be " +
                                                                               "found."));
        Sms sms = new Sms(destinationPhoneNumber, sourcePhoneNumber, SMS_BODY_CONTENT);
        smsSender.sendSms(sms);
    }
}