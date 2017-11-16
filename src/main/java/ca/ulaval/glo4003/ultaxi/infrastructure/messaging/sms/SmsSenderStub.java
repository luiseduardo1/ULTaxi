package ca.ulaval.glo4003.ultaxi.infrastructure.messaging.sms;

import ca.ulaval.glo4003.ultaxi.domain.messaging.sms.Sms;
import ca.ulaval.glo4003.ultaxi.domain.messaging.sms.SmsSender;
import ca.ulaval.glo4003.ultaxi.domain.messaging.sms.exception.SmsSendingFailureException;

import java.util.Random;

public class SmsSenderStub implements SmsSender {

    private Random random;

    private static final int FAILURE_RANGE_HIGHER_BOUND = 1;
    private static final int HIGHEST_NUMBER_GENERATABLE = 20;

    public SmsSenderStub(Random random) {
        this.random = random;
    }

    @Override
    public void sendSms(Sms sms) {
        if (this.random.nextInt(HIGHEST_NUMBER_GENERATABLE + 1) <= FAILURE_RANGE_HIGHER_BOUND) {
            throw new SmsSendingFailureException("An unexpected error happened and SMS could not be sent.");
        }

        System.out.println(String.format(
            "Sent the following message: %s to %s from %s",
            sms.getMessageBody(),
            sms.getMessageBody(),
            sms.getSourcePhoneNumber()
        ));
    }
}
