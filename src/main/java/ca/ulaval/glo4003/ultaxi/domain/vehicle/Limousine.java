package ca.ulaval.glo4003.ultaxi.domain.vehicle;

public class Limousine extends Vehicle {

    private static final String VEHICLE_TYPE = "Limousine";

    public Limousine(String color, String model, String registrationNumber) {
        super(color, model, registrationNumber);
    }

    @Override
    public String getType() {
        return VEHICLE_TYPE;
    }
}
