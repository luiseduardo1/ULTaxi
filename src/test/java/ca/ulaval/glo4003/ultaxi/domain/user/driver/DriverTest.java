package ca.ulaval.glo4003.ultaxi.domain.user.driver;

import ca.ulaval.glo4003.ultaxi.domain.user.exception.InvalidPhoneNumberException;
import ca.ulaval.glo4003.ultaxi.domain.user.exception.InvalidSocialInsuranceNumberException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DriverTest {

    private Driver driver;

    @Before
    public void setUp() {
        driver = new Driver();
    }

    @Test
    public void givenOnlyNumbersPhoneNum_whenSetPhoneNum_thenAcceptPhoneNum() {
        String phoneNumber = "2342355678";

        driver.setPhoneNumber(phoneNumber);

        Assert.assertEquals(driver.getPhoneNumber(), phoneNumber);
    }

    @Test
    public void givenPhoneNumWithParenthesis_whenSetPhoneNum_thenAcceptPhoneNum() {
        String phoneNumber = "(234)2355678";

        driver.setPhoneNumber(phoneNumber);

        Assert.assertEquals(driver.getPhoneNumber(), phoneNumber);
    }

    @Test
    public void givenPhoneNumWithDashes_whenSetPhoneNum_thenAcceptPhoneNum() {
        String phoneNumber = "234-235-5678";

        driver.setPhoneNumber(phoneNumber);

        Assert.assertEquals(driver.getPhoneNumber(), phoneNumber);
    }

    @Test
    public void givenPhoneNumWithSpaces_whenSetPhoneNum_thenAcceptPhoneNum() {
        String phoneNumber = "234 235 5678";

        driver.setPhoneNumber(phoneNumber);

        Assert.assertEquals(driver.getPhoneNumber(), phoneNumber);
    }

    @Test
    public void givenPhoneNumWithDots_whenSetPhoneNum_thenAcceptPhoneNum() {
        String phoneNumber = "234.235.5678";

        driver.setPhoneNumber(phoneNumber);

        Assert.assertEquals(driver.getPhoneNumber(), phoneNumber);
    }

    @Test(expected = InvalidPhoneNumberException.class)
    public void givenPhoneNumWithInvalidCentralOffice_whenSetPhoneNum_thenThrowException() {
        String phoneNumber = "314 159 2653";

        driver.setPhoneNumber(phoneNumber);
    }

    @Test(expected = InvalidPhoneNumberException.class)
    public void givenPhoneNumWithInvalidNPA_whenSetPhoneNum_thenThrowException() {
        String phoneNumber = "123 234 5678";

        driver.setPhoneNumber(phoneNumber);
    }

    @Test(expected = InvalidPhoneNumberException.class)
    public void givenPhoneNumWithAlphaNumericalCharacters_whenSetPhoneNum_thenThrowException() {
        String phoneNumber = "1b3 2z4 56a8";

        driver.setPhoneNumber(phoneNumber);
    }

    @Test(expected = InvalidPhoneNumberException.class)
    public void givenPhoneNumWithSpecialCharacters_whenSetPhoneNum_thenThrowException() {
        String phoneNumber = "1!3 2?4 56!8";

        driver.setPhoneNumber(phoneNumber);
    }

    @Test(expected = InvalidPhoneNumberException.class)
    public void givenPhoneNumTooLong_whenSetPhoneNum_thenThrowException() {
        String phoneNumber = "234 235 56784";

        driver.setPhoneNumber(phoneNumber);
    }

    @Test
    public void givenValidSin_whenSetSin_thenAcceptSinOk() {
        String socialInsuranceNumber = "972487086";

        driver.setSocialInsuranceNumber(socialInsuranceNumber);

        Assert.assertEquals(driver.getSocialInsuranceNumber(), socialInsuranceNumber);
    }

    @Test
    public void givenValidSinWithDashes_whenSetSin_thenAcceptSinOk() {
        String socialInsuranceNumber = "972-487-086";

        driver.setSocialInsuranceNumber(socialInsuranceNumber);

        Assert.assertEquals(driver.getSocialInsuranceNumber(), socialInsuranceNumber);
    }

    @Test(expected = InvalidSocialInsuranceNumberException.class)
    public void givenSinWithTooManyDigits_whenSetSin_thenThrowException() {
        String socialInsuranceNumber = "9724870865";

        driver.setSocialInsuranceNumber(socialInsuranceNumber);
    }

    @Test
    public void givenValidSinWithEmptySpace_whenSetSin_thenAcceptSinOk() {
        String socialInsuranceNumber = "972 487 086";

        driver.setSocialInsuranceNumber(socialInsuranceNumber);

        Assert.assertEquals(driver.getSocialInsuranceNumber(), socialInsuranceNumber);
    }

    @Test(expected = InvalidSocialInsuranceNumberException.class)
    public void givenInvalidSin_whenSetSin_thenThrowException() {
        String socialInsuranceNumber = "234154346";

        driver.setSocialInsuranceNumber(socialInsuranceNumber);
    }

    @Test(expected = InvalidSocialInsuranceNumberException.class)
    public void givenSinWithSpecialCharacters_whenSetSin_thenThrowException() {
        String socialInsuranceNumber = "1!3 2?4 56!8";

        driver.setSocialInsuranceNumber(socialInsuranceNumber);
    }

    @Test(expected = InvalidSocialInsuranceNumberException.class)
    public void givenValidSinWithDots_whenSetSin_thenThrowException() {
        String socialInsuranceNumber = "972.487.086";

        driver.setSocialInsuranceNumber(socialInsuranceNumber);
    }

}