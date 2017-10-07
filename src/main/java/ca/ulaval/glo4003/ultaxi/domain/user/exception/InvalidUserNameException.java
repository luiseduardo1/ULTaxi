package ca.ulaval.glo4003.ultaxi.domain.user.exception;

public class InvalidUserNameException extends RuntimeException {

    public InvalidUserNameException(String name) {
        super(name);
    }
}
