package ca.ulaval.glo4003.ws.domain.request;

public class InvalidRequestException extends RuntimeException {

    public InvalidRequestException(String message) {
        super(message);
    }
}
