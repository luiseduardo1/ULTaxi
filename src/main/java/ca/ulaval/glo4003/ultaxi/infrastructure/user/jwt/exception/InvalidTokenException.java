package ca.ulaval.glo4003.ultaxi.infrastructure.user.jwt.exception;

public class InvalidTokenException extends RuntimeException {

    public InvalidTokenException(String name) {
        super(name);
    }
}
