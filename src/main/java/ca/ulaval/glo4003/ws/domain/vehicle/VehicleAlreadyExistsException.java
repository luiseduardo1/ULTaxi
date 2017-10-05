package ca.ulaval.glo4003.ws.domain.vehicle;

public class VehicleAlreadyExistsException extends RuntimeException {

    public VehicleAlreadyExistsException(String message) {
        super(message);
    }
}
