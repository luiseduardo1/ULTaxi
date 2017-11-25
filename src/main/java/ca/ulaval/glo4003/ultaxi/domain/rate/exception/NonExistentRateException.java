package ca.ulaval.glo4003.ultaxi.domain.rate.exception;

public class NonExistentRateException extends RuntimeException {

    public NonExistentRateException(String message) {
        super(message);
    }
}
