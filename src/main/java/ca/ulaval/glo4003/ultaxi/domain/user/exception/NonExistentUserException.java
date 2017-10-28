package ca.ulaval.glo4003.ultaxi.domain.user.exception;

public class NonExistentUserException extends RuntimeException {
    public NonExistentUserException(String message) {
        super(message);
    }
}
