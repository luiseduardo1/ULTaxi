package ca.ulaval.glo4003.ultaxi.domain.user.exception;

public class SinAlreadyExistException extends RuntimeException {

    public SinAlreadyExistException(String message) {
        super(message);
    }
}
