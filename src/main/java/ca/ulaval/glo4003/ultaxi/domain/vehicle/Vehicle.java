package ca.ulaval.glo4003.ultaxi.domain.vehicle;

import ca.ulaval.glo4003.ultaxi.domain.user.driver.Driver;
import ca.ulaval.glo4003.ultaxi.domain.vehicle.exception.InvalidVehicleAssociationException;
import ca.ulaval.glo4003.ultaxi.domain.vehicle.exception.InvalidVehicleDissociationException;

public abstract class Vehicle {

    protected String color;
    protected String model;
    protected String registrationNumber;

    private Driver driver = null;

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

    public void associateDriver(Driver driver) {
        if (this.driver != null || driver == null) {
            throw new InvalidVehicleAssociationException("Can't associate this driver with this vehicle. It may be " +
                                                             "because this vehicle already has a driver or the driver" +
                                                             " is not valid.");
        }

        this.driver = driver;
    }

    public void dissociateDriver() {
        if (driver == null) {
            throw new InvalidVehicleDissociationException("There is no driver associated with this vehicle.");
        }

        driver = null;
    }

    public abstract VehicleType getType();
}
