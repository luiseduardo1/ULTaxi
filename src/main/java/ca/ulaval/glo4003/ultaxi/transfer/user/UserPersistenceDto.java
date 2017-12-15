package ca.ulaval.glo4003.ultaxi.transfer.user;

import ca.ulaval.glo4003.ultaxi.domain.user.PhoneNumber;
import ca.ulaval.glo4003.ultaxi.domain.user.Role;
import ca.ulaval.glo4003.ultaxi.domain.user.SocialInsuranceNumber;
import ca.ulaval.glo4003.ultaxi.domain.vehicle.Vehicle;
import ca.ulaval.glo4003.ultaxi.utils.hashing.HashingStrategy;

public class UserPersistenceDto {

    private String username;
    private String password;
    private HashingStrategy hashingStrategy;
    private String firstName;
    private String lastName;
    private String emailAddress;
    private PhoneNumber phoneNumber;
    private SocialInsuranceNumber socialInsuranceNumber;
    private Role role;
    private Vehicle vehicle;
    private String currentTransportRequestId;

    public UserPersistenceDto(String username, String password, PhoneNumber phoneNumber,
                               String emailAddress, HashingStrategy hashingStrategy,
                              String firstName, String lastName,
                              SocialInsuranceNumber socialInsuranceNumber) {
        this.username = username;
        this.password = password;
        this.hashingStrategy = hashingStrategy;
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
        this.socialInsuranceNumber = socialInsuranceNumber;
    }

    public UserPersistenceDto(String username, String password, PhoneNumber phoneNumber,
                              String emailAddress, HashingStrategy hashingStrategy) {
        this.username = username;
        this.password = password;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
        this.hashingStrategy = hashingStrategy;
    }

    public UserPersistenceDto(){}

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
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

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public String getCurrentTransportRequestId() {
        return currentTransportRequestId;
    }

    public void setCurrentTransportRequestId(String currentTransportRequestId) {
        this.currentTransportRequestId = currentTransportRequestId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public PhoneNumber getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(PhoneNumber phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public HashingStrategy getHashingStrategy() {
        return hashingStrategy;
    }

    public void setHashingStrategy(HashingStrategy hashingStrategy) {
        this.hashingStrategy = hashingStrategy;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
