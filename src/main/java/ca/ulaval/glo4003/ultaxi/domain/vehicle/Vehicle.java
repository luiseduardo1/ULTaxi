package ca.ulaval.glo4003.ultaxi.domain.vehicle;

import ca.ulaval.glo4003.ultaxi.domain.user.driver.Driver;

public abstract class Vehicle {

    protected String color;
    protected String model;
    protected String registrationNumber;

    public Driver driver;

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

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public abstract VehicleType getType();
}
