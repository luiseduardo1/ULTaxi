package ca.ulaval.glo4003.ws.domain.messaging.email;

public class InvalidEmailTypeException extends Exception {

    public InvalidEmailTypeException(String message) {
        super(message);
    }
}
