package ca.ulaval.glo4003.ultaxi.domain.user;

import ca.ulaval.glo4003.ultaxi.domain.user.exception.InvalidSocialInsuranceNumberException;
import ca.ulaval.glo4003.ultaxi.utils.LuhnAlgorithm;
import ca.ulaval.glo4003.ultaxi.utils.StringUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SocialInsuranceNumber {

    private static final String SOCIAL_INSURANCE_NUMBER_REGEX = "^((\\d{3}[\\s-]?){2}\\d{3})|(\\d{9})$";

    private final String number;

    public SocialInsuranceNumber(String socialInsuranceNumber) {
        if (!isSocialInsuranceNumberValid(socialInsuranceNumber)) {
            throw new InvalidSocialInsuranceNumberException("The social insurance number given is invalid.");
        }

        this.number = socialInsuranceNumber;
    }

    public String getNumber() {
        return this.number;
    }

    private boolean isSocialInsuranceNumberValid(String socialInsuranceNumber) {
        Pattern pattern = Pattern.compile(SOCIAL_INSURANCE_NUMBER_REGEX);
        Matcher matcher = pattern.matcher(socialInsuranceNumber);

        if (matcher.matches()) {
            String socialInsuranceNumberWithNonDigit = StringUtil.replaceNonDigitWithEmptySpace(socialInsuranceNumber);
            int[] digits = StringUtil.convertStringToIntegerArray(socialInsuranceNumberWithNonDigit);
            return LuhnAlgorithm.checkLuhnAlgorithm(digits);
        }
        return false;
    }
}
