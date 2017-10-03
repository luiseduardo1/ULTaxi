package ca.ulaval.glo4003.ws.domain.user.driver;

import ca.ulaval.glo4003.ws.domain.user.User;
import ca.ulaval.glo4003.ws.domain.user.exception.InvalidNasException;
import ca.ulaval.glo4003.ws.domain.user.exception.InvalidPhoneNumberException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Driver extends User {

    private static final String PHONE_REGEX = "^\\(?([2-9][0-9]{2})\\)?[-. ]?([2-9](?!11)[0-9]{2})[-. ]?([0-9]{4})$";
    private static final String NAS_REGEX = "^(\\d{3}-\\d{3}-\\d{3})|(\\d{9})$";

    private String name;
    private String lastName;
    private String phoneNumber;
    private String nas;

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
        if(!isPhoneNumberValid(phoneNumber)) {
            throw new InvalidPhoneNumberException("User has an invalid phone number.");
        }

        this.phoneNumber = phoneNumber;
    }

    public String getNas() {
        return nas;
    }

    public void setNas(String nas) {
        if(!isNasValidFormat(nas)) {
            throw new InvalidNasException("User has an invalid nas format.");
        }
        if(!(isValidNasWithLuhnAlgorithm(StringToIntArr(ReplaceNonDigitWithEmptySpace(nas))))){
            throw new InvalidNasException("User has an invalid nas.");
        }

        this.nas = nas;
    }

    private boolean isPhoneNumberValid(String phoneNumber) {
        Pattern pattern = Pattern.compile(PHONE_REGEX);
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
    }

    private boolean isNasValidFormat(String nas) {
        Pattern pattern = Pattern.compile(NAS_REGEX);
        Matcher matcher = pattern.matcher(nas);
        return matcher.matches();
    }


    private static int[] StringToIntArr(String nas) {
        String[] s = nas.split("");
        int[] result = new int[s.length];
        for (int i = 0; i < s.length; i++) {
            result[i] = Integer.parseInt(s[i]);
        }
        return result;
    }

    private static String ReplaceNonDigitWithEmptySpace(String nonDigitNumber){
        return nonDigitNumber.replaceAll("\\D", "");
    }

    private static boolean isValidNasWithLuhnAlgorithm(int[] digits) {
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
