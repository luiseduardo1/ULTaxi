package ca.ulaval.glo4003.ultaxi.transfer.user.driver;

public class DriverSearchParameters {

    private final String socialInsuranceNumber;
    private final String firstName;
    private final String lastName;

    public DriverSearchParameters(String socialInsuranceNumber, String firstName, String lastName) {
        this.socialInsuranceNumber = socialInsuranceNumber;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getSocialInsuranceNumber() {
        return socialInsuranceNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
