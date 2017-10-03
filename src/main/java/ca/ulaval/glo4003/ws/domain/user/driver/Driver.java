package ca.ulaval.glo4003.ws.domain.user.driver;

import ca.ulaval.glo4003.ws.domain.user.User;
import ca.ulaval.glo4003.ws.domain.user.exception.InvalidPhoneNumberException;
import ca.ulaval.glo4003.ws.domain.user.exception.InvalidSinException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Driver extends User {

    private static final String PHONE_REGEX = "^\\(?([2-9][0-9]{2})\\)?[-. ]?([2-9](?!11)[0-9]{2})[-. ]?([0-9]{4})$";
    private static final String SIN_REGEX = "^((\\d{3}[\\s-]?){2}\\d{3})|(\\d{9})$";
    private static final String NON_DIGITS_REGEX = "\\D";

    private String name;
    private String lastName;
    private String phoneNumber;
    private String sin;

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
        if (!isSinValid(sin)) {
            throw new InvalidSinException("User has an invalid sin format.");
        }

        this.sin = sin;
    }

    private boolean isPhoneNumberValid(String phoneNumber) {
        Pattern pattern = Pattern.compile(PHONE_REGEX);
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
    }

    private boolean isSinValid(String sin) {
        Pattern pattern = Pattern.compile(SIN_REGEX);
        Matcher matcher = pattern.matcher(sin);

        if (matcher.matches())
            return isValidSinWithLuhnAlgorithm(sin);
        return false;
    }


    private static int[] StringToIntArr(String sin) {
        String[] s = sin.split("");
        int[] result = new int[s.length];
        for (int i = 0; i < s.length; i++) {
            result[i] = Integer.parseInt(s[i]);
        }
        return result;
    }

    private static String ReplaceNonDigitWithEmptySpace(String nonDigitNumber) {
        return nonDigitNumber.replaceAll(NON_DIGITS_REGEX, "");
    }

    //Luhn algorithm code come from Wikipedia , url : https://fr.wikipedia.org/wiki/Formule_de_Luhn
    private static boolean isValidSinWithLuhnAlgorithm(String sin) {

        String sinWithNonDigit = ReplaceNonDigitWithEmptySpace(sin);

        int[] digits = StringToIntArr(sinWithNonDigit);

        int sum = 0;
        int length = digits.length;
        for (int i = 0; i < length; i++) {
            // get digits in reverse order
            int digit = digits[length - i - 1];

            // every 2nd number multiply with 2
            if (i % 2 == 1) {
                digit *= 2;
            }
            sum += digit > 9 ? digit - 9 : digit;
        }
        return sum % 10 == 0;
    }

}
