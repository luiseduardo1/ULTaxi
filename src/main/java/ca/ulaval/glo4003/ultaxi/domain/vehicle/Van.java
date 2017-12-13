package ca.ulaval.glo4003.ultaxi.domain.vehicle;

public class Van extends Vehicle {

    public Van(String color, String model, String registrationNumber) {
        super(color, model, registrationNumber);
    }

    @Override
    public VehicleType getType() {
        return VehicleType.VAN;
    }
}
