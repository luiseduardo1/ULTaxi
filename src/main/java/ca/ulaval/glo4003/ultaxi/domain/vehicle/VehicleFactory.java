package ca.ulaval.glo4003.ultaxi.domain.vehicle;

import ca.ulaval.glo4003.ultaxi.domain.vehicle.exception.InvalidVehicleTypeException;

public final class VehicleFactory {

    private VehicleFactory() {
        throw new AssertionError("Instantiating utility class...");
    }

    public static Vehicle getVehicle(String type, String color, String model, String registrationNumber) {
        VehicleType formattedType;
        try {
            formattedType = VehicleType.valueOf(type.toUpperCase().trim());
        } catch (NullPointerException | IndexOutOfBoundsException | IllegalArgumentException exception) {
            throw new InvalidVehicleTypeException(String.format("%s is not a valid vehicle type.", type));
        }

        switch (formattedType) {
            case CAR:
                return new Car(color, model, registrationNumber);
            case VAN:
                return new Van(color, model, registrationNumber);
            case LIMOUSINE:
                return new Limousine(color, model, registrationNumber);
            default:
                throw new InvalidVehicleTypeException(
                    String.format("%s is not a valid vehicle type.", type)
                );
        }
    }
}
