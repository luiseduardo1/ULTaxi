package ca.ulaval.glo4003.ws.infrastructure.user.JWT;

public class InvalidTokenException extends RuntimeException {
    public InvalidTokenException(String name) {
        super(name);
    }
}
