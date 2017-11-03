package ca.ulaval.glo4003.ultaxi.domain.messaging.messagingtask;

import ca.ulaval.glo4003.ultaxi.domain.messaging.sms.Sms;
import ca.ulaval.glo4003.ultaxi.domain.messaging.sms.SmsSender;
import ca.ulaval.glo4003.ultaxi.domain.messaging.sms.exception.SmsSendingFailureException;
import net.jodah.failsafe.Failsafe;
import net.jodah.failsafe.RetryPolicy;

import java.util.concurrent.TimeUnit;

public class SendDriverHasArrivedSmsTask implements MessagingTask {

    private static final String SMS_BODY_CONTENT = "A driver has answered your request and is waiting for you at the " +
        "scheduled point.";
    private static final int SECONDS_BETWEEN_RETRY_ATTEMPT = 60;

    private final String sourcePhoneNumber;
    private final String destinationPhoneNumber;
    private final SmsSender smsSender;

    public SendDriverHasArrivedSmsTask(String sourcePhoneNumber, String destinationPhoneNumber, SmsSender smsSender) {
        this.sourcePhoneNumber = sourcePhoneNumber;
        this.destinationPhoneNumber = destinationPhoneNumber;
        this.smsSender = smsSender;
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
            .abortIf(this::hasCourseStarted)
            .withDelay(SECONDS_BETWEEN_RETRY_ATTEMPT, TimeUnit.SECONDS);
    }

    private boolean hasCourseStarted(Object object) {
        // TODO: Add the call to check if the course has started
        return false;
    }

    public void execute() {
        Sms sms = new Sms(destinationPhoneNumber, sourcePhoneNumber, SMS_BODY_CONTENT);
        smsSender.sendSms(sms);
    }
}