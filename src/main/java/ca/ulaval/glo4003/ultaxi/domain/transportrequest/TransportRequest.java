package ca.ulaval.glo4003.ultaxi.domain.transportrequest;

import ca.ulaval.glo4003.ultaxi.domain.geolocation.Geolocation;
import ca.ulaval.glo4003.ultaxi.domain.money.Money;
import ca.ulaval.glo4003.ultaxi.domain.rate.Rate;
import ca.ulaval.glo4003.ultaxi.domain.transportrequest.exception.InvalidTransportRequestCompletionException;
import ca.ulaval.glo4003.ultaxi.domain.transportrequest.exception.InvalidTransportRequestStatusException;
import ca.ulaval.glo4003.ultaxi.domain.user.driver.Driver;
import ca.ulaval.glo4003.ultaxi.domain.vehicle.VehicleType;
import ca.ulaval.glo4003.ultaxi.domain.vehicle.exception.InvalidVehicleTypeException;
import ca.ulaval.glo4003.ultaxi.utils.distanceCalculator.DistanceCalculatorStrategy;

import java.math.BigDecimal;
import java.util.UUID;

public class TransportRequest {

    private String id = UUID.randomUUID().toString();
    private String clientUsername;
    private Geolocation startingPosition;
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

    public void updateStatus(TransportRequestStatus newStatus) {
        if (newStatus == TransportRequestStatus.ARRIVED && this.status != TransportRequestStatus.ACCEPTED) {
            throw new InvalidTransportRequestStatusException(String.format("The status can't be updated to %s when " +
                            "the actual status is %s.", newStatus,
                    status));
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
        this.status = TransportRequestStatus.ACCEPTED;
    }

    public void complete(Driver driver) {
        if (driver.getCurrentTransportRequestId() != this.id) {
            throw new InvalidTransportRequestCompletionException(
                    "The transport request you are trying to complete is not assigned to the driver");
        }

        this.status = TransportRequestStatus.COMPLETED;
        driver.unassignTransportRequestId();
    }

    public Money calculateTotalAmount(Rate rate, Geolocation endingPosition,
                                      DistanceCalculatorStrategy distanceCalculatorStrategy) {
        Double distance = calculateDistance(endingPosition, distanceCalculatorStrategy);
        this.totalAmount = new Money(BigDecimal.valueOf(distance).multiply(rate.getValue()));
        return this.totalAmount;
    }

    private Double calculateDistance(Geolocation endingPosition,
                                     DistanceCalculatorStrategy distanceCalculatorStrategy) {
        return distanceCalculatorStrategy.calculDistance(startingPosition.getLatitude(), startingPosition
                .getLongitude(), endingPosition.getLatitude(), endingPosition.getLongitude());
    }

}
