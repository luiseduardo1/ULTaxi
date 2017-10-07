package ca.ulaval.glo4003.ultaxi.domain.vehicle;

public class Car extends Vehicle {

    private static final String VEHICLE_TYPE = "Car";

    public Car(String color, String model, String registrationNumber) {
        super(color, model, registrationNumber);
    }

    @Override
    public String getType() {
        return VEHICLE_TYPE;
    }
}
