package ca.ulaval.glo4003.ultaxi.infrastructure.user;

public final class CommonUser {

    public CommonUser(){}

    private static final String UPDATING_CLIENT_NAME = "Sam";
    private static final String UPDATING_CLIENT_PASSWORD = "myPassword";
    private static final String UPDATING_CLIENT_EMAIL = "sam123@ultaxi.ca";

    public String getUpdatingClientName() {
        return UPDATING_CLIENT_NAME;
    }

    public String getUpdatingClientPassword() {
        return UPDATING_CLIENT_PASSWORD;
    }

    public String getUpdatingClientEmail() {
        return UPDATING_CLIENT_EMAIL;
    }

}
