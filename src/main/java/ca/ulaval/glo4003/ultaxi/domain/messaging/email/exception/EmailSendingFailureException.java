package ca.ulaval.glo4003.ultaxi.domain.messaging.email.exception;

public class EmailSendingFailureException extends RuntimeException {

    public EmailSendingFailureException(final Throwable cause) {
        super(cause);
    }
}
