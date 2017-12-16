package ca.ulaval.glo4003.ultaxi.domain.transportrequest;

import ca.ulaval.glo4003.ultaxi.domain.geolocation.Geolocation;
import ca.ulaval.glo4003.ultaxi.domain.money.Money;
import ca.ulaval.glo4003.ultaxi.domain.rate.Rate;
import ca.ulaval.glo4003.ultaxi.domain.transportrequest.exception.InvalidTransportRequestCompletionException;
import ca.ulaval.glo4003.ultaxi.domain.transportrequest.exception.InvalidTransportRequestStatusException;
import ca.ulaval.glo4003.ultaxi.domain.user.driver.Driver;
import ca.ulaval.glo4003.ultaxi.domain.vehicle.VehicleType;
import ca.ulaval.glo4003.ultaxi.domain.vehicle.exception.InvalidVehicleTypeException;

import java.util.UUID;

public class TransportRequest {

    private String id = UUID.randomUUID().toString();
    private String clientUsername;
    private Geolocation startingPosition;
    private Geolocation endingPosition;
    private String note;
    private VehicleType vehicleType;
    private TransportRequestStatus status = TransportRequestStatus.PENDING;
    private Money totalAmount;

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

    public Geolocation getEndingPosition() {
        return endingPosition;
    }

    public void setEndingPosition(Geolocation endingPosition) {
        this.endingPosition = endingPosition;
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

    public Money getTotalAmount() {
        return this.totalAmount;
    }

    public TransportRequestStatus getTransportRequestStatus() {
        return this.status;
    }

    public boolean isAvailable() {
        if (this.status == TransportRequestStatus.PENDING) {
            return true;
        }
        return false;
    }

    public void setToAvailable() {
        if (this.status != TransportRequestStatus.ACCEPTED) {
            throw new InvalidTransportRequestStatusException(
                "The status can't be updated to PENDING when the current status is not ACCEPTED."
            );
        }
        this.status = TransportRequestStatus.PENDING;
    }

    public void setToUnavailable() {
        if (this.status != TransportRequestStatus.PENDING) {
            throw new InvalidTransportRequestStatusException(
                "The status can't be updated to ACCEPTED when the current status is not PENDING."
            );
        }
        this.status = TransportRequestStatus.ACCEPTED;
    }

    public void setToCompleted(Driver driver, Geolocation endingPosition) {
        if (driver.getCurrentTransportRequestId() != this.id) {
            throw new InvalidTransportRequestCompletionException(
                    "The transport request you are trying to complete is not assigned to the driver");
        }
        if (this.status != TransportRequestStatus.STARTED) {
            throw new InvalidTransportRequestStatusException(
                    "The status can't be set to COMPLETED when the current status is not STARTED."
            );
        }
        this.endingPosition = endingPosition;
        this.status = TransportRequestStatus.COMPLETED;
        driver.unassignTransportRequestId();
    }

    public void calculateTotalAmount(Rate rate) {
        this.totalAmount = rate.calculateTotalAmount(this.startingPosition, this.endingPosition);
    }

    public void setToArrived() {
        if (this.status != TransportRequestStatus.ACCEPTED) {
            throw new InvalidTransportRequestStatusException(
                "The status can't be set to ARRIVED when the current status is not ACCEPTED."
            );
        }
        this.status = TransportRequestStatus.ARRIVED;
    }

    public void setToStarted() {
        if (this.status != TransportRequestStatus.ARRIVED) {
            throw new InvalidTransportRequestStatusException(
                "The status can't be set to STARTED when the current status is not ARRIVED."
            );
        }
        this.status = TransportRequestStatus.STARTED;
    }
}
