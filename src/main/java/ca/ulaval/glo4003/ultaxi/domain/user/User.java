package ca.ulaval.glo4003.ultaxi.domain.user;

import ca.ulaval.glo4003.ultaxi.domain.user.exception.InvalidEmailAddressException;
import ca.ulaval.glo4003.ultaxi.domain.user.exception.InvalidHashingStrategyException;
import ca.ulaval.glo4003.ultaxi.domain.user.exception.InvalidPasswordException;
import ca.ulaval.glo4003.ultaxi.domain.user.exception.InvalidUsernameException;
import ca.ulaval.glo4003.ultaxi.utils.hashing.HashingStrategy;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

public abstract class User {

    private String username;
    private String password;
    private PhoneNumber phoneNumber;
    private String emailAddress;
    private HashingStrategy hashingStrategy;

    public User(String username, String password, PhoneNumber phoneNumber, String emailAddress, HashingStrategy
        hashingStrategy) {
        setUsername(username);
        setPassword(password, hashingStrategy);
        setPhoneNumber(phoneNumber);
        setEmailAddress(emailAddress);
    }

    public abstract Role getRole();

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        if (!isUsernameValid(username)) {
            throw new InvalidUsernameException(
                String.format("%s is not a valid name.", username)
            );
        }
        this.username = username.toLowerCase().trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password, HashingStrategy hashingStrategy) {
        if (isBlank(password)) {
            throw new InvalidPasswordException("This password is not valid.");
        }

        if (hashingStrategy == null) {
            throw new InvalidHashingStrategyException("The hashing strategy is not valid.");
        }
        this.hashingStrategy = hashingStrategy;
        this.password = hashingStrategy.hashWithRandomSalt(password);
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
        if (!isValidEmailAddress(emailAddress)) {
            throw new InvalidEmailAddressException(
                String.format("%s is not a valid email address", emailAddress)
            );
        }
        this.emailAddress = emailAddress;
    }

    public boolean areValidCredentials(String username, String plainTextPassword) {
        return username != null
            && plainTextPassword != null
            && username.toLowerCase().trim().equals(this.username)
            && hashingStrategy.areEquals(plainTextPassword, password);
    }

    private boolean isUsernameValid(String name) {
        return !isBlank(name) && !name.contains("@");
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    private boolean isValidEmailAddress(String emailAddress) {
        try {
            InternetAddress email = new InternetAddress(emailAddress);
            email.validate();
        } catch (AddressException e) {
            return false;
        }
        return true;
    }
}
