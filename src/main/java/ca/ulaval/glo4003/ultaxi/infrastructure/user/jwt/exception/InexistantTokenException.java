package ca.ulaval.glo4003.ultaxi.infrastructure.user.jwt.exception;

public class InexistantTokenException extends RuntimeException {

    public InexistantTokenException(String name) {
        super(name);
    }

}


