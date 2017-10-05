package ca.ulaval.glo4003.ws.domain.user;

public class User {

    private String name;
    private String password;
    private String emailAddress;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        if (!isValid()) {
            throw new InvalidUserNameException(
                String.format("%s is not a valid name.", name)
            );
        }
    }

    private boolean isValid() {
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
}
