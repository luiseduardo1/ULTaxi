package ca.ulaval.glo4003.ultaxi.domain.user.exception;

public class InvalidHashingStrategyException extends RuntimeException {

    public InvalidHashingStrategyException(String message) {
        super(message);
    }
}
