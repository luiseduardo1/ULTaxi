package ca.ulaval.glo4003.ultaxi.domain.vehicle;

public class Car extends Vehicle {

    public Car(String color, String model, String registrationNumber) {
        super(color, model, registrationNumber);
    }

    @Override
    public VehicleType getType() {
        return VehicleType.CAR;
    }
}
