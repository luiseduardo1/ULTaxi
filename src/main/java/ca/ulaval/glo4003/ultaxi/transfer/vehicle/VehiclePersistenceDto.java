package ca.ulaval.glo4003.ultaxi.transfer.vehicle;

import ca.ulaval.glo4003.ultaxi.domain.user.driver.Driver;
import ca.ulaval.glo4003.ultaxi.domain.vehicle.VehicleType;

public class VehiclePersistenceDto {

    public VehiclePersistenceDto(String type, String color, String model, String registrationNumber) {
        this.color = color;
        this.model = model;
        this.registrationNumber = registrationNumber;
        this.type = type;
    }

    public VehiclePersistenceDto() {}

    private String color;
    private String model;
    private String registrationNumber;
    private Driver driver;
    private VehicleType vehicleType;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private String type;


    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }


}
