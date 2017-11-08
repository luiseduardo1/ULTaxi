package ca.ulaval.glo4003.ultaxi.domain.messaging.sms.exception;

public class SmsSendingFailureException extends RuntimeException {

    public SmsSendingFailureException(String message) {
        super(message);
    }
}
