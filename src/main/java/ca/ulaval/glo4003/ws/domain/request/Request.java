package ca.ulaval.glo4003.ws.domain.request;

import ca.ulaval.glo4003.ws.domain.request.geolocation.Geolocation;
import ca.ulaval.glo4003.ws.domain.vehicle.InvalidVehicleTypeException;

public class Request {

    private static final String VEHICLE_TYPES = "car|van|limousine";

    private Geolocation geolocation;
    private String id;
    private String note;
    private String vehicleType;

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
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
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
