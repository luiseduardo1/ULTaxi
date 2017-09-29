package ca.ulaval.glo4003.ws.domain.user;

public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException(String name) {
        super(name);
    }
}
