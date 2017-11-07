package ca.ulaval.glo4003.ultaxi.domain.messaging.sms.exception;

public class UnrecoverableSmsSendingFailureException extends RuntimeException {

    public UnrecoverableSmsSendingFailureException(String message) {
        super(message);
    }
}
