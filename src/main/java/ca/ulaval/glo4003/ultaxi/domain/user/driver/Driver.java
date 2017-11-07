package ca.ulaval.glo4003.ultaxi.domain.user.driver;

import ca.ulaval.glo4003.ultaxi.domain.user.Role;
import ca.ulaval.glo4003.ultaxi.domain.user.User;
import ca.ulaval.glo4003.ultaxi.domain.user.exception.InvalidPhoneNumberException;
import ca.ulaval.glo4003.ultaxi.domain.user.exception.InvalidSocialInsuranceNumberException;
import ca.ulaval.glo4003.ultaxi.domain.vehicle.Vehicle;
import ca.ulaval.glo4003.ultaxi.domain.vehicle.VehicleType;
import ca.ulaval.glo4003.ultaxi.domain.vehicle.exception.InvalidVehicleAssociationException;
import ca.ulaval.glo4003.ultaxi.utils.LuhnAlgorithm;
import ca.ulaval.glo4003.ultaxi.utils.StringUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Driver extends User {

    private static final String PHONE_REGEX = "^\\(?([2-9][0-9]{2})\\)?[-. ]?([2-9](?!11)[0-9]{2})[-. ]?([0-9]{4})$";
    private static final String SOCIAL_INSURANCE_NUMBER_REGEX = "^((\\d{3}[\\s-]?){2}\\d{3})|(\\d{9})$";

    private String name;
    private String lastName;
    private String phoneNumber;
    private String socialInsuranceNumber;

    public Vehicle vehicle;

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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        if (!isPhoneNumberValid(phoneNumber)) {
            throw new InvalidPhoneNumberException("User has an invalid phone number.");
        }

        this.phoneNumber = phoneNumber;
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

    public void dissociateVehicle(Vehicle vehicle) {
        if (this.vehicle == null || vehicle == null) {
            throw new InvalidVehicleAssociationException("Can't dissociate this vehicle: it may be because the driver" +
                                                             " has no vehicle associated or the given vehicle is " +
                                                             "invalid.");
        }

        vehicle.dissociateDriver(this);
        this.vehicle = null;
    }

    private boolean isPhoneNumberValid(String phoneNumber) {
        Pattern pattern = Pattern.compile(PHONE_REGEX);
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
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
