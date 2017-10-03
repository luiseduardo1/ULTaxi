package ca.ulaval.glo4003.ws.domain.request;

import ca.ulaval.glo4003.ws.domain.vehicle.InvalidVehicleTypeException;

public class Request {

    private static final String VEHICLE_TYPES = "car|van|limousine";

    private String id ;
    private String location;
    private String note;
    private String vehicleType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getVehiculeType() {
        return vehicleType;
    }

    public void setVehiculeType(String vehicleType) {
        if (isInvalidVehicleType(vehicleType)) {
            throw new InvalidVehicleTypeException(
                String.format("%s is not a valid vehicle type.", vehicleType));
        }
        this.vehicleType = vehicleType;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public boolean isInvalidVehicleType(String vehicleType) {
        return vehicleType.matches(VEHICLE_TYPES);
    }
}
