package ca.ulaval.glo4003.ws.domain.vehicle;

public class InvalidVehicleTypeException extends RuntimeException {

    public InvalidVehicleTypeException(String message) {
        super(message);
    }
}
