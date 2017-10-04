package ca.ulaval.glo4003.ws.domain.request;

import ca.ulaval.glo4003.ws.domain.geolocation.Geolocation;
import ca.ulaval.glo4003.ws.domain.vehicle.InvalidVehicleTypeException;

public class Request {

    private enum VehicleType {
        CAR, LIMOUSINE, VAN;
    }

    private Geolocation geolocation;
    private String id;
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

    public void setId(String id) {
        this.id = id;
    }

    public String getVehicleType() {
        return vehicleType.name();
    }

    public void setVehicleType(String vehicleType) {
        switch (vehicleType.toLowerCase()){
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
        this.note = note;
    }

}
