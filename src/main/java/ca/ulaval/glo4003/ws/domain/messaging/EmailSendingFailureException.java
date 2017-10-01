package ca.ulaval.glo4003.ws.domain.messaging;

public class EmailSendingFailureException extends RuntimeException {

    public EmailSendingFailureException(final Throwable cause) {
        super(cause);
    }
}
