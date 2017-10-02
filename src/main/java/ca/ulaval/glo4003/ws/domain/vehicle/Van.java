package ca.ulaval.glo4003.ws.domain.vehicle;

public class Van extends Vehicle {

    private static final String VEHICLE_TYPE = "Van";

    public Van(String color, String model, String registrationNumber) {
        super(color, model, registrationNumber);
    }

    @Override
    public String getType() {
        return VEHICLE_TYPE;
    }
}
