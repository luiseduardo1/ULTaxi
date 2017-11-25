package ca.ulaval.glo4003.ultaxi.domain.user;

import ca.ulaval.glo4003.ultaxi.domain.user.exception.InvalidPhoneNumberException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneNumber {

    private static final String PHONE_REGEX = "^\\(?([2-9][0-9]{2})\\)?[-. ]?([2-9](?!11)[0-9]{2})[-. ]?([0-9]{4})$";

    private final String number;

    public PhoneNumber(String phoneNumber) {
        if (!isPhoneNumberValid(phoneNumber)) {
            throw new InvalidPhoneNumberException("The phone number is invalid.");
        }

        this.number = phoneNumber;
    }

    public String getNumber() {
        return number;
    }

    private boolean isPhoneNumberValid(String phoneNumber) {
        Pattern pattern = Pattern.compile(PHONE_REGEX);
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
    }

}
