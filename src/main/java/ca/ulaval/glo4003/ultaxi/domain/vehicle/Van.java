package ca.ulaval.glo4003.ultaxi.domain.vehicle;

public class Van extends Vehicle {

    private static final VehicleType VEHICLE_TYPE = VehicleType.VAN;

    public Van(String color, String model, String registrationNumber) {
        super(color, model, registrationNumber);
    }

    @Override
    public VehicleType getType() {
        return VEHICLE_TYPE;
    }
}
