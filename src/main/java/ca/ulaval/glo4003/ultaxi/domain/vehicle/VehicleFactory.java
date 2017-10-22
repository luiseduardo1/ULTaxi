package ca.ulaval.glo4003.ultaxi.domain.vehicle;

import ca.ulaval.glo4003.ultaxi.domain.vehicle.exception.InvalidVehicleTypeException;
import ca.ulaval.glo4003.ultaxi.utils.StringUtil;

public final class VehicleFactory {

    private VehicleFactory() {
        throw new AssertionError("Instantiating utility class...");
    }

    public static Vehicle getVehicle(String type, String color, String model, String registrationNumber) {
        VehicleType formattedType;
        try {
            formattedType = VehicleType.valueOf(StringUtil.capitalize(type).trim());
        } catch (NullPointerException | IndexOutOfBoundsException | IllegalArgumentException exception) {
            throw new InvalidVehicleTypeException(String.format("%s is not a valid vehicle type.", type));
        }

        switch (formattedType) {
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
