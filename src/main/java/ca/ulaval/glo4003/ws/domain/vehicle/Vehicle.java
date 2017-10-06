package ca.ulaval.glo4003.ws.domain.vehicle;

public abstract class Vehicle {

    protected String color;
    protected String model;
    protected String registrationNumber;

    public Vehicle(String color, String model, String registrationNumber) {
        this.color = color;
        this.model = model;
        this.registrationNumber = registrationNumber;
    }

    public String getColor() {
        return color;
    }

    public String getModel() {
        return model;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public abstract String getType();
}
