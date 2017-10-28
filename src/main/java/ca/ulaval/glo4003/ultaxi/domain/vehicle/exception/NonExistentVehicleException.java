package ca.ulaval.glo4003.ultaxi.domain.vehicle.exception;

public class NonExistentVehicleException extends RuntimeException {

    public NonExistentVehicleException(String message) {
        super(message);
    }
}
