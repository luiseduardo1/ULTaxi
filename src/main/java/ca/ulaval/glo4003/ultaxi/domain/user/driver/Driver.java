package ca.ulaval.glo4003.ultaxi.domain.user.driver;

import ca.ulaval.glo4003.ultaxi.domain.transportrequest.TransportRequest;
import ca.ulaval.glo4003.ultaxi.domain.transportrequest.exception.InvalidTransportRequestAssignationException;
import ca.ulaval.glo4003.ultaxi.domain.user.Role;
import ca.ulaval.glo4003.ultaxi.domain.user.User;
import ca.ulaval.glo4003.ultaxi.domain.user.exception.InvalidSocialInsuranceNumberException;
import ca.ulaval.glo4003.ultaxi.domain.vehicle.Vehicle;
import ca.ulaval.glo4003.ultaxi.domain.vehicle.VehicleType;
import ca.ulaval.glo4003.ultaxi.domain.vehicle.exception.InvalidVehicleAssociationException;
import ca.ulaval.glo4003.ultaxi.domain.vehicle.exception.InvalidVehicleDissociationException;
import ca.ulaval.glo4003.ultaxi.utils.LuhnAlgorithm;
import ca.ulaval.glo4003.ultaxi.utils.StringUtil;
import ca.ulaval.glo4003.ultaxi.utils.hashing.HashingStrategy;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Driver extends User {

    private static final String SOCIAL_INSURANCE_NUMBER_REGEX = "^((\\d{3}[\\s-]?){2}\\d{3})|(\\d{9})$";

    private String firstName;
    private String lastName;
    private String socialInsuranceNumber;

    private TransportRequest transportRequest;
    private Vehicle vehicle;

    public Driver(String username, String password, String phoneNumber, String emailAddress,
        HashingStrategy hashingStrategy, String firstName, String lastName, String socialInsuranceNumber) {
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

    public String getSocialInsuranceNumber() {
        return socialInsuranceNumber;
    }

    public void setSocialInsuranceNumber(String socialInsuranceNumber) {
        if (!isValidSocialInsuranceNumber(socialInsuranceNumber)) {
            throw new InvalidSocialInsuranceNumberException("User has an invalid socialInsuranceNumber format.");
        }

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

    public TransportRequest getTransportRequest() {
        return transportRequest;
    }

    public void assignTransportRequest(TransportRequest transportRequest) {
        boolean transportRequestAssignationIsValid = (
            this.transportRequest == null
                && transportRequest.isAvailable()
                && (this.vehicle != null
                && this.vehicle.getType() == transportRequest.getVehicleType()
                || this.vehicle == null)
        );
        if (!transportRequestAssignationIsValid) {
            throw new InvalidTransportRequestAssignationException("Can't make one-to-one assignation");
        }
        this.transportRequest = transportRequest;
        transportRequest.setUnavailable();
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
}
