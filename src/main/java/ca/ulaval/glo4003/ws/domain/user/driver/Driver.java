package ca.ulaval.glo4003.ws.domain.user.driver;

import ca.ulaval.glo4003.ws.domain.user.User;
import ca.ulaval.glo4003.ws.domain.user.exception.InvalidPhoneNumberException;
import ca.ulaval.glo4003.ws.domain.user.exception.InvalidSinException;
import ca.ulaval.glo4003.ws.utils.LuhnAlgorithm;
import ca.ulaval.glo4003.ws.utils.StringUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Driver extends User {

    private static final String PHONE_REGEX = "^\\(?([2-9][0-9]{2})\\)?[-. ]?([2-9](?!11)[0-9]{2})[-. ]?([0-9]{4})$";
    private static final String SIN_REGEX = "^((\\d{3}[\\s-]?){2}\\d{3})|(\\d{9})$";

    private String name;
    private String lastName;
    private String phoneNumber;
    private String sin;

    private LuhnAlgorithm luhnAlgorithm;
    private StringUtil stringUtil;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        if (!isPhoneNumberValid(phoneNumber)) {
            throw new InvalidPhoneNumberException("User has an invalid phone number.");
        }

        this.phoneNumber = phoneNumber;
    }

    public String getSin() {
        return sin;
    }

    public void setSin(String sin) {
        if (!isValidSin(sin)) {
            throw new InvalidSinException("User has an invalid sin format.");
        }

        this.sin = sin;
    }

    private boolean isPhoneNumberValid(String phoneNumber) {
        Pattern pattern = Pattern.compile(PHONE_REGEX);
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
    }

    private boolean isValidSin(String sin) {
        Pattern pattern = Pattern.compile(SIN_REGEX);
        Matcher matcher = pattern.matcher(sin);

        if (matcher.matches()){
            String sinWithNonDigit = stringUtil.ReplaceNonDigitWithEmptySpace(sin);
            int[] digits = stringUtil.StringToIntArr(sinWithNonDigit);
            return luhnAlgorithm.checkLuhnAlgorithm(digits);
        }
        return false;
    }

}
