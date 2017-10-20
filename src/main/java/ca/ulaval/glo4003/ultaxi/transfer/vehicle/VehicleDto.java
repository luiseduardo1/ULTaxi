package ca.ulaval.glo4003.ultaxi.transfer.vehicle;

import ca.ulaval.glo4003.ultaxi.domain.vehicle.VehicleType;

public class VehicleDto {

    private VehicleType type;
    private String color;
    private String model;
    private String registrationNumber;

    public VehicleType getType() {
        return type;
    }

    public void setType(VehicleType type) {
        this.type = type;
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
}
