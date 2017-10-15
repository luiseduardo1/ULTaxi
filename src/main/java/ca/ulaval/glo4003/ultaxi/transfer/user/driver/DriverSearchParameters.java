package ca.ulaval.glo4003.ultaxi.transfer.user.driver;

public class DriverSearchParameters {

    private final String sin;
    private final String firstName;
    private final String lastName;

    public DriverSearchParameters(String sin, String firstName, String lastName) {
        this.sin = sin;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getSin() {
        return sin;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
