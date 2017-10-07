package ca.ulaval.glo4003.ultaxi.domain.user;

import ca.ulaval.glo4003.ultaxi.domain.user.exception.InvalidPasswordException;
import ca.ulaval.glo4003.ultaxi.domain.user.exception.InvalidUserNameException;

public class User {

    private String userName;
    private String password;
    private Role role;
    private String emailAddress;
    private HashingStrategy hashingStrategy;

    public String getPassword() {
        return hashingStrategy.hash(password);
    }

    public void setPassword(String password) {
        if (isBlank(password)) {
            throw new InvalidPasswordException("This password is not valid.");
        }
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        if (!isNameValid(userName)) {
            throw new InvalidUserNameException(
                String.format("%s is not a valid name.", userName)
            );
        }
        this.userName = userName.toLowerCase().trim();
    }

    private boolean isNameValid(String name) {
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
            && user.getUserName().equals(this.userName)
            && hashingStrategy.areEquals(this.password, user.getPassword());
    }
}
