package ca.ulaval.glo4003.ultaxi.domain.transportrequest.exception;

public class ClientAlreadyHasAnActiveTransportRequestException extends RuntimeException {

    public ClientAlreadyHasAnActiveTransportRequestException(String message) {
        super(message);
    }
}
