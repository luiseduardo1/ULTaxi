package ca.ulaval.glo4003.ultaxi.domain.rate.exception;

public class RateAlreadyExistsException extends RuntimeException {

    public RateAlreadyExistsException(String message) {
        super(message);
    }
}
