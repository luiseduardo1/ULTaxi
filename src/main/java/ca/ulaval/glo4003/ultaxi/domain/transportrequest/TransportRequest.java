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
    private TransportRequestStatus transportRequestStatus = TransportRequestStatus.Pending;
    private String clientUsername;

    public TransportRequest() {
    }

    public TransportRequest(Geolocation startingPosition, String note, VehicleType vehicleType, String clientUsername) {
        this.startingPosition = startingPosition;
        this.note = note;
        this.vehicleType = vehicleType;
        this.transportRequestStatus = TransportRequestStatus.Pending;
        this.clientUsername = clientUsername;
    }

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

    public TransportRequestStatus getTransportRequestStatus() {
        return this.transportRequestStatus;
    }

    public void setTransportRequestStatus(TransportRequestStatus transportRequestStatus) {
        this.transportRequestStatus = transportRequestStatus;
    }

    public void setClientUsername(String clientUsername) {
        this.clientUsername = clientUsername;
    }

    public String getClientUsername() {
        return clientUsername;
    }

}
