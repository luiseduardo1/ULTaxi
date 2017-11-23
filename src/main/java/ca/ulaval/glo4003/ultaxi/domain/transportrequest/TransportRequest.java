package ca.ulaval.glo4003.ultaxi.domain.transportrequest;

import ca.ulaval.glo4003.ultaxi.domain.geolocation.Geolocation;
import ca.ulaval.glo4003.ultaxi.domain.transportrequest.exception.InvalidTransportRequestStatusException;
import ca.ulaval.glo4003.ultaxi.domain.vehicle.VehicleType;
import ca.ulaval.glo4003.ultaxi.domain.vehicle.exception.InvalidVehicleTypeException;

import java.util.UUID;

public class TransportRequest {

    private String id = UUID.randomUUID().toString();
    private String clientUsername;
    private Geolocation startingPosition;
    private String note;
    private VehicleType vehicleType;
    private TransportRequestStatus status = TransportRequestStatus.PENDING;

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

    public void setId(String id) {
        this.id = id;
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
        return this.status;
    }

    public void updateStatus(TransportRequestStatus newStatus) {
        if (newStatus == TransportRequestStatus.ARRIVED && this.status != TransportRequestStatus.PENDING) {
            throw new InvalidTransportRequestStatusException(String.format("The status can't be updated to %s when the actual status is %s.", newStatus, status));
        }

        this.status = newStatus;
    }

    public boolean isAvailable() {
        if (this.status == TransportRequestStatus.PENDING) {
            return true;
        }
        return false;
    }

    public void setAvailable() {
        this.status = TransportRequestStatus.PENDING;
    }

    public void setUnavailable() {
        this.status = TransportRequestStatus.STARTED;
    }
}
