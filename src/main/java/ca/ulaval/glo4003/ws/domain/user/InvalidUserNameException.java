package ca.ulaval.glo4003.ws.domain.user;

public class InvalidUserNameException extends RuntimeException {
    public InvalidUserNameException(String name) {
        super(name);
    }
}
