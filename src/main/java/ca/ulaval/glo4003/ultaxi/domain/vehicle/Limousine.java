package ca.ulaval.glo4003.ultaxi.domain.vehicle;

public class Limousine extends Vehicle {

    public Limousine(String color, String model, String registrationNumber) {
        super(color, model, registrationNumber);
    }

    @Override
    public VehicleType getType() {
        return VehicleType.LIMOUSINE;
    }
}
