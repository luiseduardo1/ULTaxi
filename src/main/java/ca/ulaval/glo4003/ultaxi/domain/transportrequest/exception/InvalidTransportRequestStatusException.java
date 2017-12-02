package ca.ulaval.glo4003.ultaxi.domain.transportrequest.exception;

public class InvalidTransportRequestStatusException extends RuntimeException {

    public InvalidTransportRequestStatusException(String message) {
        super(message);
    }
}