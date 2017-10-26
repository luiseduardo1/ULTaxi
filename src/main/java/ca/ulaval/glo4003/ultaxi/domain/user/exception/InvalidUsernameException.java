package ca.ulaval.glo4003.ultaxi.domain.user.exception;

public class InvalidUsernameException extends RuntimeException {

    public InvalidUsernameException(String name) {
        super(name);
    }
}
