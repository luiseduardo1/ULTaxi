package ca.ulaval.glo4003.ws.domain.vehicle;

public class InvalidRegistrationNumberException extends RuntimeException {

    public InvalidRegistrationNumberException(String message) {
        super(message);
    }
}
