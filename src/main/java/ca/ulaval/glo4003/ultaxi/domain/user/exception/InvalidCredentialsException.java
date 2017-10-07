package ca.ulaval.glo4003.ultaxi.domain.user.exception;

public class InvalidCredentialsException extends RuntimeException {

    public InvalidCredentialsException(String name) {
        super(name);
    }
}
