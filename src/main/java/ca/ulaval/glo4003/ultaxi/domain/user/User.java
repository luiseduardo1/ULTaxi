package ca.ulaval.glo4003.ultaxi.domain.user;

import ca.ulaval.glo4003.ultaxi.domain.user.exception.InvalidEmailAddressException;
import ca.ulaval.glo4003.ultaxi.domain.user.exception.InvalidHashingStrategyException;
import ca.ulaval.glo4003.ultaxi.domain.user.exception.InvalidPasswordException;
import ca.ulaval.glo4003.ultaxi.domain.user.exception.InvalidUsernameException;
import ca.ulaval.glo4003.ultaxi.utils.hashing.HashingStrategy;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

public class User {

    private String username;
    private String password;
    private Role role;
    private String emailAddress;
    private HashingStrategy hashingStrategy;

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

    private boolean isUsernameValid(String name) {
        return !isBlank(name) && !name.contains("@");
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
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

    private boolean isValidEmailAddress(String emailAddress) {
        try {
            InternetAddress email = new InternetAddress(emailAddress);
            email.validate();
        } catch (AddressException e) {
            return false;
        }
        return true;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public boolean areCredentialsValid(String username, String plainTextPassword) {
        return username != null
            && plainTextPassword != null
            && username.equals(this.username)
            && hashingStrategy.areEquals(plainTextPassword, password);
    }
}
