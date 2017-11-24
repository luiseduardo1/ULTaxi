package ca.ulaval.glo4003.ultaxi.domain.user.driver;

import ca.ulaval.glo4003.ultaxi.domain.transportrequest.TransportRequest;
import ca.ulaval.glo4003.ultaxi.domain.transportrequest.exception.InvalidTransportRequestAssignationException;
import ca.ulaval.glo4003.ultaxi.domain.user.Role;
import ca.ulaval.glo4003.ultaxi.domain.user.SocialInsuranceNumber;
import ca.ulaval.glo4003.ultaxi.domain.user.User;
import ca.ulaval.glo4003.ultaxi.domain.user.exception.InvalidSocialInsuranceNumberException;
import ca.ulaval.glo4003.ultaxi.domain.vehicle.Vehicle;
import ca.ulaval.glo4003.ultaxi.domain.vehicle.VehicleType;
import ca.ulaval.glo4003.ultaxi.domain.vehicle.exception.InvalidVehicleAssociationException;
import ca.ulaval.glo4003.ultaxi.domain.vehicle.exception.InvalidVehicleDissociationException;
import ca.ulaval.glo4003.ultaxi.utils.LuhnAlgorithm;
import ca.ulaval.glo4003.ultaxi.utils.StringUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Driver extends User {
    
    private String name;
    private String lastName;
    private SocialInsuranceNumber socialInsuranceNumber;

    private TransportRequest transportRequest;
    private Vehicle vehicle;

    public Driver() {
        this.setRole(Role.DRIVER);
    }

    public Driver(String firstName, String lastName, SocialInsuranceNumber socialInsuranceNumber) {
        this.setName(firstName);
        this.setLastName(lastName);
        this.setSocialInsuranceNumber(socialInsuranceNumber);
        this.setRole(Role.DRIVER);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public SocialInsuranceNumber getSocialInsuranceNumber() {
        return socialInsuranceNumber;
    }

    public void setSocialInsuranceNumber(SocialInsuranceNumber socialInsuranceNumber) {
        this.socialInsuranceNumber = socialInsuranceNumber;
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

    public VehicleType getVehicleType() {
        VehicleType vehicleType = null;
        if (vehicle != null) {
            vehicleType = vehicle.getType();
        }

        return vehicleType;
    }

    public Vehicle getVehicle() {
        return vehicle;
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

    public TransportRequest getTransportRequest() {
        return transportRequest;
    }

    public void setTransportRequest(TransportRequest transportRequest) {
        this.transportRequest = transportRequest;
    }

    public void assignTransportRequest(TransportRequest transportRequest) {
        boolean transportRequestAssignationIsValid = (this.transportRequest == null
                && transportRequest.isAvailable()
                && (this.vehicle != null && this.vehicle.getType() ==
                transportRequest.getVehicleType() || this.vehicle == null));
        if (!transportRequestAssignationIsValid) {
            throw new InvalidTransportRequestAssignationException("Can't make one-to-one assignation");
        }
        this.transportRequest = transportRequest;
        transportRequest.setUnavailable();
    }
}
