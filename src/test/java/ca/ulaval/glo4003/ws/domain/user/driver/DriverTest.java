package ca.ulaval.glo4003.ws.domain.user.driver;

import ca.ulaval.glo4003.ws.domain.user.exception.InvalidPhoneNumberException;
import ca.ulaval.glo4003.ws.domain.user.exception.InvalidSinException;
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
        String phoneNum = "2342355678";

        driver.setPhoneNumber(phoneNum);

        Assert.assertEquals(driver.getPhoneNumber(), phoneNum);
    }

    @Test
    public void givenPhoneNumWithParenthesis_whenSetPhoneNum_thenAcceptPhoneNum() {
        String phoneNum = "(234)2355678";

        driver.setPhoneNumber(phoneNum);

        Assert.assertEquals(driver.getPhoneNumber(), phoneNum);
    }

    @Test
    public void givenPhoneNumWithDashes_whenSetPhoneNum_thenAcceptPhoneNum() {
        String phoneNum = "234-235-5678";

        driver.setPhoneNumber(phoneNum);

        Assert.assertEquals(driver.getPhoneNumber(), phoneNum);
    }

    @Test
    public void givenPhoneNumWithSpaces_whenSetPhoneNum_thenAcceptPhoneNum() {
        String phoneNum = "234 235 5678";

        driver.setPhoneNumber(phoneNum);

        Assert.assertEquals(driver.getPhoneNumber(), phoneNum);
    }

    @Test
    public void givenPhoneNumWithDots_whenSetPhoneNum_thenAcceptPhoneNum() {
        String phoneNum = "234.235.5678";

        driver.setPhoneNumber(phoneNum);

        Assert.assertEquals(driver.getPhoneNumber(), phoneNum);
    }

    @Test(expected = InvalidPhoneNumberException.class)
    public void givenPhoneNumWithInvalidCentralOffice_whenSetPhoneNum_thenThrowException() {
        String phoneNum = "314 159 2653";

        driver.setPhoneNumber(phoneNum);
    }

    @Test(expected = InvalidPhoneNumberException.class)
    public void givenPhoneNumWithInvalidNPA_whenSetPhoneNum_thenThrowException() {
        String phoneNum = "123 234 5678";

        driver.setPhoneNumber(phoneNum);
    }

    @Test(expected = InvalidPhoneNumberException.class)
    public void givenPhoneNumWithAlphaNumericalCharacters_whenSetPhoneNum_thenThrowException() {
        String phoneNum = "1b3 2z4 56a8";

        driver.setPhoneNumber(phoneNum);
    }

    @Test(expected = InvalidPhoneNumberException.class)
    public void givenPhoneNumWithSpecialCharacters_whenSetPhoneNum_thenThrowException() {
        String phoneNum = "1!3 2?4 56!8";

        driver.setPhoneNumber(phoneNum);
    }

    @Test(expected = InvalidPhoneNumberException.class)
    public void givenPhoneNumTooLong_whenSetPhoneNum_thenThrowException() {
        String phoneNum = "234 235 56784";

        driver.setPhoneNumber(phoneNum);
    }

    @Test
    public void givenValidSin_whenSetSin_thenAcceptSinOk() {
        String sin = "972487086";

        driver.setSin(sin);

        Assert.assertEquals(driver.getSin(), sin);
    }

    @Test
    public void givenValidSinWithDasches_whenSetSin_thenAcceptSinOk() {
        String sin = "972-487-086";

        driver.setSin(sin);

        Assert.assertEquals(driver.getSin(), sin);
    }

    @Test(expected = InvalidSinException.class)
    public void givenSinWithMoreNumber_whenSetSin_thenThrowExceptio() {
        String sin = "9724870865";

        driver.setSin(sin);
    }

    @Test(expected = InvalidSinException.class)
    public void givenValidSinWithEmptySpace_whenSetSin_thenThrowException() {
        String sin = "972 487 086";

        driver.setSin(sin);

        Assert.assertEquals(driver.getSin(), sin);
    }

    @Test(expected = InvalidSinException.class)
    public void givenInvalidSin_whenSetSin_thenThrowException() {
        String sin = "234154346";

        driver.setSin(sin);
    }

    @Test(expected = InvalidSinException.class)
    public void givenSinWithSpecialCharacters_whenSetSin_thenTrowxception() {
        String sin = "1!3 2?4 56!8";

        driver.setSin(sin);
    }

    @Test(expected = InvalidSinException.class)
    public void givenValidSinWithDots_whenSetSin_thenTrowxception() {
        String sin = "972.487.086";

        driver.setSin(sin);
    }

}