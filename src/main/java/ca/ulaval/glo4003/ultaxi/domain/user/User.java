package ca.ulaval.glo4003.ultaxi.domain.user;

import ca.ulaval.glo4003.ultaxi.domain.user.exception.InvalidPasswordException;
import ca.ulaval.glo4003.ultaxi.domain.user.exception.InvalidUserNameException;
import ca.ulaval.glo4003.ultaxi.utils.hashing.HashingStrategy;

import javax.validation.constraints.NotNull;

public class User {

    private String username;
    private String password;
    private Role role;
    private String emailAddress;
    private HashingStrategy hashingStrategy;

    public String getPassword() {
        return hashingStrategy.hash(password);
    }

    public void setPassword(@NotNull String password) {
        if (isBlank(password)) {
            throw new InvalidPasswordException("This password is not valid.");
        }
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        if (!isUserNameValid(username)) {
            throw new InvalidUserNameException(
                String.format("%s is not a valid name.", username)
            );
        }
        this.username = username.toLowerCase().trim();
    }

    private boolean isUserNameValid(String name) {
        return !isBlank(name) && !name.contains("@");
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setHashingStrategy(HashingStrategy hashingStrategy) {
        this.hashingStrategy = hashingStrategy;
    }

    public boolean isTheSameAs(User user) {
        return user != null
            && user.getUsername().equals(this.username)
            && hashingStrategy.areEquals(this.password, user.getPassword());
    }
}
