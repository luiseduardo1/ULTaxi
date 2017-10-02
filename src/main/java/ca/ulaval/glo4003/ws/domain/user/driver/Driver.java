package ca.ulaval.glo4003.ws.domain.user.driver;

import ca.ulaval.glo4003.ws.domain.user.User;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Driver extends User {

    private static final String PHONE_REGEX = "^\\(?([2-9][0-9]{2})\\)?[-. ]?([2-9](?!11)[0-9]{2})[-. ]?([0-9]{4})$";

    private String phoneNumber;
    private String nas;

    public String getPhoneNumber() { return phoneNumber; }

    public void setPhoneNumber(String phoneNumber) {
        if(!isPhoneNumberValid(phoneNumber)) {
            throw new IllegalArgumentException();
        }

        this.phoneNumber = phoneNumber;
    }

    public String getNas() { return nas; }

    public void setNas(String nas) { this.nas = nas; }

    private boolean isPhoneNumberValid(String phoneNumber) {
        Pattern pattern = Pattern.compile(PHONE_REGEX);
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
    }
}
