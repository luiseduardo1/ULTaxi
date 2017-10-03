package ca.ulaval.glo4003.ws.domain.user.driver;

import ca.ulaval.glo4003.ws.domain.user.exception.InvalidNasException;
import ca.ulaval.glo4003.ws.domain.user.exception.InvalidPhoneNumberException;
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

    @Test (expected = InvalidPhoneNumberException.class)
    public void givenPhoneNumWithInvalidCentralOffice_whenSetPhoneNum_thenThrowException() {
        String phoneNum = "314 159 2653";

        driver.setPhoneNumber(phoneNum);
    }

    @Test (expected = InvalidPhoneNumberException.class)
    public void givenPhoneNumWithInvalidNPA_whenSetPhoneNum_thenThrowException() {
        String phoneNum = "123 234 5678";

        driver.setPhoneNumber(phoneNum);
    }

    @Test (expected = InvalidPhoneNumberException.class)
    public void givenPhoneNumWithAlphaNumericalCharacters_whenSetPhoneNum_thenThrowException() {
        String phoneNum = "1b3 2z4 56a8";

        driver.setPhoneNumber(phoneNum);
    }

    @Test (expected = InvalidPhoneNumberException.class)
    public void givenPhoneNumWithSpecialCharacters_whenSetPhoneNum_thenThrowException() {
        String phoneNum = "1!3 2?4 56!8";

        driver.setPhoneNumber(phoneNum);
    }

    @Test (expected = InvalidPhoneNumberException.class)
    public void givenPhoneNumTooLong_whenSetPhoneNum_thenThrowException() {
        String phoneNum = "234 235 56784";

        driver.setPhoneNumber(phoneNum);
    }

    @Test
    public void givenValidNas_whenSetNas_thenAcceptNasOk(){
        String nas = "972487086";

        driver.setNas(nas);

        Assert.assertEquals(driver.getNas(), nas);
    }

    @Test
    public void givenValidNasWithDasches_whenSetNas_thenAcceptNasOk() {
        String nas = "972-487-086";

        driver.setNas(nas);

        Assert.assertEquals(driver.getNas(), nas);
    }

    @Test (expected = InvalidNasException.class)
    public void givenNasWithMoreNumber_whenSetNas_thenThrowExceptio() {
        String nas = "9724870865";

        driver.setNas(nas);
    }

    @Test (expected = InvalidNasException.class)
    public void givenValidNasWithEmptySpace_whenSetNas_thenThrowException() {
        String nas = "972 487 086";

        driver.setNas(nas);

        Assert.assertEquals(driver.getNas(), nas);
    }

    @Test (expected = InvalidNasException.class)
    public void givenInvalidNas_whenSetNas_thenThrowException(){
        String nas = "234154346";

        driver.setNas(nas);
    }

    @Test (expected = InvalidNasException.class)
    public void givenNasWithSpecialCharacters_whenSetNas_thenTrowxception(){
        String nas = "1!3 2?4 56!8";

        driver.setNas(nas);
    }

    @Test (expected = InvalidNasException.class)
    public void givenValidNasWithDots_whenSetNas_thenTrowxception(){
        String nas = "972.487.086";

        driver.setNas(nas);
    }

}