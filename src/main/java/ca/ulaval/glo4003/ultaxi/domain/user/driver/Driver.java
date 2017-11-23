package ca.ulaval.glo4003.ultaxi.domain.user.driver;

import ca.ulaval.glo4003.ultaxi.domain.transportrequest.TransportRequest;
import ca.ulaval.glo4003.ultaxi.domain.transportrequest.TransportRequestStatus;
import ca.ulaval.glo4003.ultaxi.domain.transportrequest.exception.DriverHasNoTransportRequestAssignedException;
import ca.ulaval.glo4003.ultaxi.domain.user.Role;
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

    private static final String SOCIAL_INSURANCE_NUMBER_REGEX = "^((\\d{3}[\\s-]?){2}\\d{3})|(\\d{9})$";

    private String name;
    private String lastName;
    private String socialInsuranceNumber;
    private Vehicle vehicle;
    private String currentTransportRequestId;
    private TransportRequest transportRequest;

    public Driver() {
        this.setRole(Role.DRIVER);
    }

    public Driver(String firstName, String lastName, String socialInsuranceNumber) {
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

    public String getSocialInsuranceNumber() {
        return socialInsuranceNumber;
    }

    public void setSocialInsuranceNumber(String socialInsuranceNumber) {
        if (!isValidSocialInsuranceNumber(socialInsuranceNumber)) {
            throw new InvalidSocialInsuranceNumberException("User has an invalid socialInsuranceNumber format.");
        }

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

    private boolean isValidSocialInsuranceNumber(String socialInsuranceNumber) {
        Pattern pattern = Pattern.compile(SOCIAL_INSURANCE_NUMBER_REGEX);
        Matcher matcher = pattern.matcher(socialInsuranceNumber);

        if (matcher.matches()) {
            String socialInsuranceNumberWithNonDigit = StringUtil.replaceNonDigitWithEmptySpace(socialInsuranceNumber);
            int[] digits = StringUtil.convertStringToIntegerArray(socialInsuranceNumberWithNonDigit);
            return LuhnAlgorithm.checkLuhnAlgorithm(digits);
        }
        return false;
    }

    public String getCurrentTransportRequestId() {
        if (this.currentTransportRequestId == null) {
            throw new DriverHasNoTransportRequestAssignedException("This driver don't have a transport request assigned.");
        }
        return this.currentTransportRequestId;
    }

    public TransportRequest getTransportRequest() {
        return this.transportRequest;
    }
}
