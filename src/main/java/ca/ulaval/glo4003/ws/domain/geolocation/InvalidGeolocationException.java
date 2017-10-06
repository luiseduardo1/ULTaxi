package ca.ulaval.glo4003.ws.domain.geolocation;

public class InvalidGeolocationException extends RuntimeException {

    public InvalidGeolocationException(String message) {
        super(message);
    }
}
