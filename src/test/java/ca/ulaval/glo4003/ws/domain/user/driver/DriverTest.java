package ca.ulaval.glo4003.ws.domain.user.driver;

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

    @Test (expected = IllegalArgumentException.class)
    public void givenPhoneNumWithInvalidCentralOffice_whenSetPhoneNum_thenThrowException() {
        String phoneNum = "314 159 2653";

        driver.setPhoneNumber(phoneNum);
    }

    @Test (expected = IllegalArgumentException.class)
    public void givenPhoneNumWithInvalidNPA_whenSetPhoneNum_thenThrowException() {
        String phoneNum = "123 234 5678";

        driver.setPhoneNumber(phoneNum);
    }
}