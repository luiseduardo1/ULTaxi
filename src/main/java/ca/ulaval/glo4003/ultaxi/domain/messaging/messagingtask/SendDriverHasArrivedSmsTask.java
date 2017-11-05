package ca.ulaval.glo4003.ultaxi.domain.messaging.messagingtask;

import ca.ulaval.glo4003.ultaxi.domain.messaging.sms.Sms;
import ca.ulaval.glo4003.ultaxi.domain.messaging.sms.SmsSender;
import ca.ulaval.glo4003.ultaxi.domain.messaging.sms.exception.SmsSendingFailureException;
import ca.ulaval.glo4003.ultaxi.domain.transportrequest.TransportRequest;
import ca.ulaval.glo4003.ultaxi.domain.transportrequest.TransportRequestRepository;
import ca.ulaval.glo4003.ultaxi.domain.transportrequest.TransportRequestStatus;
import net.jodah.failsafe.Failsafe;
import net.jodah.failsafe.RetryPolicy;

import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

public class SendDriverHasArrivedSmsTask implements MessagingTask {

    private static final String SMS_BODY_CONTENT = "A driver has answered your request and is waiting for you at the " +
        "scheduled point.";
    private static final int SECONDS_BETWEEN_RETRY_ATTEMPT = 60;

    private final String sourcePhoneNumber;
    private final String destinationPhoneNumber;
    private final SmsSender smsSender;
    private final Predicate<Object> hasCourseStarted;

    public SendDriverHasArrivedSmsTask(String sourcePhoneNumber, String destinationPhoneNumber, SmsSender smsSender,
        String transportRequestId, TransportRequestRepository transportRequestRepository) {
        this.sourcePhoneNumber = sourcePhoneNumber;
        this.destinationPhoneNumber = destinationPhoneNumber;
        this.smsSender = smsSender;
        // We do not need the object passed as a parameter by the `failsafe` package
        this.hasCourseStarted = __ -> {
            TransportRequest transportRequest = transportRequestRepository.findById(transportRequestId);
            return transportRequest != null
                && transportRequest.getTransportRequestStatus() == TransportRequestStatus.STARTED;
        };
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
            .abortIf(hasCourseStarted::test)
            .withDelay(SECONDS_BETWEEN_RETRY_ATTEMPT, TimeUnit.SECONDS);
    }

    public void execute() {
        Sms sms = new Sms(destinationPhoneNumber, sourcePhoneNumber, SMS_BODY_CONTENT);
        smsSender.sendSms(sms);
    }
}