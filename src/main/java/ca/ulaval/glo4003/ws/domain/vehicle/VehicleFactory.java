package ca.ulaval.glo4003.ws.domain.vehicle;

public final class VehicleFactory {

    private VehicleFactory() {
        throw new AssertionError("Instantiating utility class...");
    }

    public static Vehicle getVehicle(String type, String color, String model, String registrationNumber) {

        if (type == null) {
            throw new InvalidVehicleTypeException("Vehicle type cannot be null.");
        }

        if (type.toLowerCase().equals("car")) {
            return new Car(color, model, registrationNumber);
        } else if (type.toLowerCase().equals("van")) {
            return new Van(color, model, registrationNumber);
        } else if (type.toLowerCase().equals("limousine")) {
            return new Limousine(color, model, registrationNumber);
        }

        throw new InvalidVehicleTypeException(
            String.format("%s is not a valid vehicle type.", type)
        );
    }
}
