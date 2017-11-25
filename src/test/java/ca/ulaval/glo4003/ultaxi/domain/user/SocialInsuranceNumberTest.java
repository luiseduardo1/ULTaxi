package ca.ulaval.glo4003.ultaxi.domain.user;

import ca.ulaval.glo4003.ultaxi.domain.user.exception.InvalidSocialInsuranceNumberException;
import org.junit.Test;

public class SocialInsuranceNumberTest {

    @Test
    public void givenValidSocialInsuranceNumber_whenCreateSocialInsuranceNumber_thenNoExceptionIsThrown() {
        String socialInsuranceNumber = "972487086";

        new SocialInsuranceNumber(socialInsuranceNumber);
    }

    @Test
    public void
    givenValidSocialInsuranceNumberWithDashes_whenCreateSocialInsuranceNumber_thenNoExceptionIsThrown() {
        String socialInsuranceNumber = "972-487-086";

        new SocialInsuranceNumber(socialInsuranceNumber);
    }

    @Test(expected = InvalidSocialInsuranceNumberException.class)
    public void
    givenSocialInsuranceNumberWithTooManyDigits_whenCreateSocialInsuranceNumber_thenThrowsInvalidSocialInsuranceNumberException() {
        String socialInsuranceNumber = "9724870865";

        new SocialInsuranceNumber(socialInsuranceNumber);
    }

    @Test
    public void
    givenValidSocialInsuranceNumberWithEmptySpace_whenCreateSocialInsuranceNumber_thenAcceptSocialInsuranceNumber() {
        String socialInsuranceNumber = "972 487 086";

        new SocialInsuranceNumber(socialInsuranceNumber);
    }

    @Test(expected = InvalidSocialInsuranceNumberException.class)
    public void
    givenInvalidSocialInsuranceNumber_whenCreateSocialInsuranceNumber_thenThrowsInvalidSocialInsuranceNumberException
        () {
        String socialInsuranceNumber = "234154346";

        new SocialInsuranceNumber(socialInsuranceNumber);
    }

    @Test(expected = InvalidSocialInsuranceNumberException.class)
    public void
    givenSocialInsuranceNumberWithSpecialCharacters_whenCreateSocialInsuranceNumber_thenThrowsInvalidSocialInsuranceNumberException() {
        String socialInsuranceNumber = "1!3 2?4 56!8";

        new SocialInsuranceNumber(socialInsuranceNumber);
    }

    @Test(expected = InvalidSocialInsuranceNumberException.class)
    public void
    givenValidSocialInsuranceNumberWithDots_whenCreateSocialInsuranceNumber_thenThrowsInvalidSocialInsuranceNumberException() {
        String socialInsuranceNumber = "972.487.086";

        new SocialInsuranceNumber(socialInsuranceNumber);
    }
}
