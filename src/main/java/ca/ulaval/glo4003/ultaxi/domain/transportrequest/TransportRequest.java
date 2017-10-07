package ca.ulaval.glo4003.ultaxi.domain.transportrequest;

import ca.ulaval.glo4003.ultaxi.domain.geolocation.Geolocation;
import ca.ulaval.glo4003.ultaxi.domain.vehicle.exception.InvalidVehicleTypeException;

import java.util.UUID;

public class TransportRequest {

    private String id = UUID.randomUUID().toString();
    private Geolocation geolocation;
    private String note;
    private VehicleType vehicleType;

    public Geolocation getGeolocation() {
        return geolocation;
    }

    public void setGeolocation(Geolocation geolocation) {
        this.geolocation = geolocation;
    }

    public String getId() {
        return id;
    }

    public String getVehicleType() {
        return vehicleType.name();
    }

    public void setVehicleType(String vehicleType) {
        switch (vehicleType.toLowerCase()) {
            case "car":
                this.vehicleType = VehicleType.CAR;
                break;
            case "van":
                this.vehicleType = VehicleType.VAN;
                break;
            case "limousine":
                this.vehicleType = VehicleType.LIMOUSINE;
                break;
            default:
                throw new InvalidVehicleTypeException(
                    String.format("%s is not a valid vehicle type.", vehicleType));
        }
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        if (note == null) {
            this.note = "";
        }
        this.note = note;
    }

    private enum VehicleType {
        CAR, LIMOUSINE, VAN;
    }

}
