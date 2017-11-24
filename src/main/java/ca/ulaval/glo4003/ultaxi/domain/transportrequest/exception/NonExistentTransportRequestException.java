package ca.ulaval.glo4003.ultaxi.domain.transportrequest.exception;

public class NonExistentTransportRequestException extends RuntimeException {

    public NonExistentTransportRequestException(String message) {
        super(message);
    }
}
