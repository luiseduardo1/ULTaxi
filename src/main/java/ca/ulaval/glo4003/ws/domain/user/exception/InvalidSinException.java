package ca.ulaval.glo4003.ws.domain.user.exception;

public class InvalidSinException extends RuntimeException {
    public InvalidSinException(String message) {
        super(message);
    }
}