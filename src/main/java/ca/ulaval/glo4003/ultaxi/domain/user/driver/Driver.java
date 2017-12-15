package ca.ulaval.glo4003.ultaxi.domain.user.driver;

import ca.ulaval.glo4003.ultaxi.domain.transportrequest.TransportRequest;
import ca.ulaval.glo4003.ultaxi.domain.transportrequest.exception.DriverHasNoTransportRequestAssignedException;
import ca.ulaval.glo4003.ultaxi.domain.transportrequest.exception.InvalidTransportRequestAssignationException;
import ca.ulaval.glo4003.ultaxi.domain.user.PhoneNumber;
import ca.ulaval.glo4003.ultaxi.domain.user.Role;
import ca.ulaval.glo4003.ultaxi.domain.user.SocialInsuranceNumber;
import ca.ulaval.glo4003.ultaxi.domain.user.User;
import ca.ulaval.glo4003.ultaxi.domain.vehicle.Vehicle;
import ca.ulaval.glo4003.ultaxi.domain.vehicle.VehicleType;
import ca.ulaval.glo4003.ultaxi.domain.vehicle.exception.InvalidVehicleAssociationException;
import ca.ulaval.glo4003.ultaxi.domain.vehicle.exception.InvalidVehicleDissociationException;
import ca.ulaval.glo4003.ultaxi.utils.hashing.HashingStrategy;

public class Driver extends User {

    private final String firstName;
    private final String lastName;
    private SocialInsuranceNumber socialInsuranceNumber;
    private Vehicle vehicle;
    private String currentTransportRequestId;

    public Driver(String username, String password, PhoneNumber phoneNumber, String emailAddress,
        HashingStrategy hashingStrategy, String firstName, String lastName,
        SocialInsuranceNumber socialInsuranceNumber) {
        super(username, password, phoneNumber, emailAddress, hashingStrategy);
        this.firstName = firstName;
        this.lastName = lastName;
        setSocialInsuranceNumber(socialInsuranceNumber);
    }

    @Override
    public Role getRole() {
        return Role.DRIVER;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public SocialInsuranceNumber getSocialInsuranceNumber() {
        return socialInsuranceNumber;
    }

    public void setSocialInsuranceNumber(SocialInsuranceNumber socialInsuranceNumber) {
        this.socialInsuranceNumber = socialInsuranceNumber;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public VehicleType getVehicleType() {
        VehicleType vehicleType = null;
        if (vehicle != null) {
            vehicleType = vehicle.getType();
        }

        return vehicleType;
    }

    public void associateVehicle(Vehicle vehicle) {
        if (this.vehicle != null || vehicle == null) {
            throw new InvalidVehicleAssociationException("Can't associate this vehicle: it may be because the driver " +
                    "already have a vehicle associated or the given vehicle " +
                    "is invalid.");
        }

        vehicle.associateDriver(this);
        this.vehicle = vehicle;
    }

    public void dissociateVehicle() {
        if (vehicle == null) {
            throw new InvalidVehicleDissociationException("Can't dissociate this vehicle: it may be because the " +
                    "driver has no vehicle associated or the given vehicle " +
                    "is invalid.");
        }
        vehicle.dissociateDriver();
        vehicle = null;
    }

    public String getCurrentTransportRequestId() {
        if (this.currentTransportRequestId == null) {
            throw new DriverHasNoTransportRequestAssignedException("This driver don't have a transport request " +
                    "assigned.");
        }
        return this.currentTransportRequestId;
    }

    public void assignTransportRequestId(TransportRequest transportRequest) {
        boolean transportRequestAssignationIsValid = (
                this.currentTransportRequestId == null
                        && transportRequest.isAvailable()
                        && (this.vehicle != null && this.vehicle.getType() ==
                        transportRequest.getVehicleType() || this.vehicle == null));

        if (!transportRequestAssignationIsValid) {
            throw new InvalidTransportRequestAssignationException("Can't make one-to-one assignation");
        }
        this.currentTransportRequestId = transportRequest.getId();
        transportRequest.setUnavailable();
    }

    public void unassignTransportRequestId() {
        this.currentTransportRequestId = null;
    }
}
