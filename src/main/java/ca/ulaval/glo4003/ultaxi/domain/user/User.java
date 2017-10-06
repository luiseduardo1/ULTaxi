package ca.ulaval.glo4003.ultaxi.domain.user;

import ca.ulaval.glo4003.ultaxi.domain.user.exception.InvalidUserNameException;

public class User {

    private String userName;
    private String password;
    private Role role;
    private String emailAddress;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
        if (!isValid()) {
            throw new InvalidUserNameException(
                String.format("%s is not a valid name.", userName)
            );
        }
    }

    private boolean isValid() {
        return !isBlank(userName) && !userName.contains("@");
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

    public boolean isTheSameAs(User user) {
        return user.getUserName().equals(this.userName)
            && user.getPassword().equals(this.password);
    }
}
