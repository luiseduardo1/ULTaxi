package ca.ulaval.glo4003.ultaxi.domain.transportrequest;

import ca.ulaval.glo4003.ultaxi.domain.geolocation.Geolocation;
import ca.ulaval.glo4003.ultaxi.domain.vehicle.VehicleType;
import ca.ulaval.glo4003.ultaxi.domain.vehicle.exception.InvalidVehicleTypeException;
import ca.ulaval.glo4003.ultaxi.utils.StringUtil;

import java.util.UUID;

public class TransportRequest {

    private final String id = UUID.randomUUID().toString();
    private Geolocation startingPosition;
    private String note;
    private VehicleType vehicleType;

    public Geolocation getStartingPosition() {
        return startingPosition;
    }

    public void setStartingPosition(Geolocation startingPosition) {
        this.startingPosition = startingPosition;
    }

    public String getId() {
        return id;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        try {
            this.vehicleType = VehicleType.valueOf(StringUtil.capitalize(vehicleType).trim());
        } catch (NullPointerException | IllegalArgumentException | IndexOutOfBoundsException exception) {
            throw new InvalidVehicleTypeException(String.format("%s is not a valid vehicle type.", vehicleType));
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

}
