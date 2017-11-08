package ca.ulaval.glo4003.ultaxi.domain.transportrequest;

import ca.ulaval.glo4003.ultaxi.domain.geolocation.Geolocation;
import ca.ulaval.glo4003.ultaxi.domain.vehicle.VehicleType;
import ca.ulaval.glo4003.ultaxi.domain.vehicle.exception.InvalidVehicleTypeException;

import java.util.UUID;

public class TransportRequest {

    private final String id = UUID.randomUUID().toString();
    private String clientUsername;
    private Geolocation startingPosition;
    private String note;
    private VehicleType vehicleType;
    private TransportRequestStatus transportRequestStatus = TransportRequestStatus.PENDING;

    public TransportRequest() {
    }

    public TransportRequest(String clientUsername, Geolocation startingPosition, String note, VehicleType vehicleType) {
        this.clientUsername = clientUsername;
        this.startingPosition = startingPosition;
        this.note = note;
        this.vehicleType = vehicleType;
    }

    public String getClientUsername() {
        return clientUsername;
    }

    public void setClientUsername(String clientUsername) {
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        if (note == null) {
            this.note = "";
        }
        this.note = note;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        try {
            this.vehicleType = VehicleType.valueOf(vehicleType.toUpperCase().trim());
        } catch (NullPointerException | IllegalArgumentException | IndexOutOfBoundsException exception) {
            throw new InvalidVehicleTypeException(String.format("%s is not a valid vehicle type.", vehicleType));
        }
    }

    public TransportRequestStatus getTransportRequestStatus() {
        return this.transportRequestStatus;
    }

    public void setTransportRequestStatus(TransportRequestStatus transportRequestStatus) {
        this.transportRequestStatus = transportRequestStatus;
    }

    public boolean isAvailable() {
        if (this.transportRequestStatus == TransportRequestStatus.PENDING) {
            return true;
        }
        return false;
    }

    public void setAvailable() {
        this.transportRequestStatus = TransportRequestStatus.PENDING;
    }

    public void setUnavailable() {
        this.transportRequestStatus = TransportRequestStatus.TAKEN;
    }
}
