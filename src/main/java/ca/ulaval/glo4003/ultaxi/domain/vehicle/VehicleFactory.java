package ca.ulaval.glo4003.ultaxi.domain.vehicle;

import ca.ulaval.glo4003.ultaxi.domain.vehicle.exception.InvalidVehicleTypeException;

public final class VehicleFactory {

    private VehicleFactory() {
        throw new AssertionError("Instantiating utility class...");
    }

    public static Vehicle getVehicle(VehicleType type, String color, String model, String registrationNumber) {

        if (type == null) {
            throw new InvalidVehicleTypeException("Vehicle type cannot be null.");
        }

        switch (type) {
            case Car:
                return new Car(color, model, registrationNumber);
            case Van:
                return new Van(color, model, registrationNumber);
            case Limousine:
                return new Limousine(color, model, registrationNumber);
            default:
                throw new InvalidVehicleTypeException(
                    String.format("%s is not a valid vehicle type.", type)
                );
        }
    }
}
