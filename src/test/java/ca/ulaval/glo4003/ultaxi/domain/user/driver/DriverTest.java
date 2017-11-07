package ca.ulaval.glo4003.ultaxi.domain.user.driver;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;

import ca.ulaval.glo4003.ultaxi.domain.user.Role;
import ca.ulaval.glo4003.ultaxi.domain.user.User;
import ca.ulaval.glo4003.ultaxi.domain.user.exception.InvalidPhoneNumberException;
import ca.ulaval.glo4003.ultaxi.domain.user.exception.InvalidSocialInsuranceNumberException;
import ca.ulaval.glo4003.ultaxi.domain.vehicle.Vehicle;
import ca.ulaval.glo4003.ultaxi.domain.vehicle.exception.InvalidVehicleAssociationException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DriverTest {

    private Driver driver;
    @Mock
    private Vehicle vehicle;

    @Before
    public void setUp() {
        driver = new Driver();
    }

    @Test
    public void givenOnlyNumbersPhoneNumber_whenSetPhoneNumber_thenAcceptPhoneNumber() {
        String phoneNumber = "2342355678";

        driver.setPhoneNumber(phoneNumber);

        Assert.assertEquals(driver.getPhoneNumber(), phoneNumber);
    }

    @Test
    public void givenPhoneNumberWithParenthesis_whenSetPhoneNumber_thenAcceptPhoneNumber() {
        String phoneNumber = "(234)2355678";

        driver.setPhoneNumber(phoneNumber);

        Assert.assertEquals(driver.getPhoneNumber(), phoneNumber);
    }

    @Test
    public void givenPhoneNumberWithDashes_whenSetPhoneNumber_thenAcceptPhoneNumber() {
        String phoneNumber = "234-235-5678";

        driver.setPhoneNumber(phoneNumber);

        Assert.assertEquals(driver.getPhoneNumber(), phoneNumber);
    }

    @Test
    public void givenPhoneNumberWithSpaces_whenSetPhoneNumber_thenAcceptPhoneNumber() {
        String phoneNumber = "234 235 5678";

        driver.setPhoneNumber(phoneNumber);

        Assert.assertEquals(driver.getPhoneNumber(), phoneNumber);
    }

    @Test
    public void givenPhoneNumberWithDots_whenSetPhoneNumber_thenAcceptPhoneNumber() {
        String phoneNumber = "234.235.5678";

        driver.setPhoneNumber(phoneNumber);

        Assert.assertEquals(driver.getPhoneNumber(), phoneNumber);
    }

    @Test(expected = InvalidPhoneNumberException.class)
    public void givenPhoneNumberWithInvalidCentralOffice_whenSetPhoneNumber_thenThrowsInvalidPhoneNumberException() {
        String phoneNumber = "314 159 2653";

        driver.setPhoneNumber(phoneNumber);
    }

    @Test(expected = InvalidPhoneNumberException.class)
    public void givenPhoneNumberWithInvalidNumberingPlanArea_whenSetPhoneNumber_thenThrowsInvalidPhoneNumberException
        () {
        String phoneNumber = "123 234 5678";

        driver.setPhoneNumber(phoneNumber);
    }

    @Test(expected = InvalidPhoneNumberException.class)
    public void givenPhoneNumberWithAlphaNumericalCharacters_whenSetPhoneNumber_thenThrowsInvalidPhoneNumberException
        () {
        String phoneNumber = "1b3 2z4 56a8";

        driver.setPhoneNumber(phoneNumber);
    }

    @Test(expected = InvalidPhoneNumberException.class)
    public void givenPhoneNumberWithSpecialCharacters_whenSetPhoneNumber_thenThrowsInvalidPhoneNumberException() {
        String phoneNumber = "1!3 2?4 56!8";

        driver.setPhoneNumber(phoneNumber);
    }

    @Test(expected = InvalidPhoneNumberException.class)
    public void givenPhoneNumberTooLong_whenSetPhoneNumber_thenThrowsInvalidPhoneNumberException() {
        String phoneNumber = "234 235 56784";

        driver.setPhoneNumber(phoneNumber);
    }

    @Test
    public void givenValidSocialInsuranceNumber_whenSetSocialInsuranceNumber_thenAcceptSocialInsuranceNumber() {
        String socialInsuranceNumber = "972487086";

        driver.setSocialInsuranceNumber(socialInsuranceNumber);

        Assert.assertEquals(driver.getSocialInsuranceNumber(), socialInsuranceNumber);
    }

    @Test
    public void
    givenValidSocialInsuranceNumberWithDashes_whenSetSocialInsuranceNumber_thenAcceptSocialInsuranceNumber() {
        String socialInsuranceNumber = "972-487-086";

        driver.setSocialInsuranceNumber(socialInsuranceNumber);

        Assert.assertEquals(driver.getSocialInsuranceNumber(), socialInsuranceNumber);
    }

    @Test(expected = InvalidSocialInsuranceNumberException.class)
    public void
    givenSocialInsuranceNumberWithTooManyDigits_whenSetSocialInsuranceNumber_thenThrowsInvalidSocialInsuranceNumberException() {
        String socialInsuranceNumber = "9724870865";

        driver.setSocialInsuranceNumber(socialInsuranceNumber);
    }

    @Test
    public void
    givenValidSocialInsuranceNumberWithEmptySpace_whenSetSocialInsuranceNumber_thenAcceptSocialInsuranceNumber() {
        String socialInsuranceNumber = "972 487 086";

        driver.setSocialInsuranceNumber(socialInsuranceNumber);

        Assert.assertEquals(driver.getSocialInsuranceNumber(), socialInsuranceNumber);
    }

    @Test(expected = InvalidSocialInsuranceNumberException.class)
    public void
    givenInvalidSocialInsuranceNumber_whenSetSocialInsuranceNumber_thenThrowsInvalidSocialInsuranceNumberException() {
        String socialInsuranceNumber = "234154346";

        driver.setSocialInsuranceNumber(socialInsuranceNumber);
    }

    @Test(expected = InvalidSocialInsuranceNumberException.class)
    public void
    givenSocialInsuranceNumberWithSpecialCharacters_whenSetSocialInsuranceNumber_thenThrowsInvalidSocialInsuranceNumberException() {
        String socialInsuranceNumber = "1!3 2?4 56!8";

        driver.setSocialInsuranceNumber(socialInsuranceNumber);
    }

    @Test(expected = InvalidSocialInsuranceNumberException.class)
    public void
    givenValidSocialInsuranceNumberWithDots_whenSetSocialInsuranceNumber_thenThrowsInvalidSocialInsuranceNumberException() {
        String socialInsuranceNumber = "972.487.086";

        driver.setSocialInsuranceNumber(socialInsuranceNumber);
    }

    @Test
    public void
    givenANewDriverUser_whenCreateDriver_thenRoleShouldBeADriver() {
        User driver = new Driver();

        assertEquals(driver.getRole(), Role.DRIVER);
    }

    @Test
    public void givenADriverWithNoVehicleAssociated_whenAssociatingVehicle_thenVehicleIsAssociated() {
        driver.associateVehicle(vehicle);

        verify(vehicle).associateDriver(driver);
    }

    @Test(expected = InvalidVehicleAssociationException.class)
    public void givenADriverWithAVehicleAssociated_whenAssociatingVehicle_thenThrowsInvalidVehicleAssociationException() {
        driver.associateVehicle(vehicle);

        driver.associateVehicle(vehicle);
    }

    @Test(expected = InvalidVehicleAssociationException.class)
    public void givenNullVehicle_whenAssociatingVehicle_thenThrowsInvalidVehicleAssociationException() {
        driver.associateVehicle(null);
    }

    @Test
    public void givenADriverWithAVehicleAssociated_whenDissociatingVehicle_thenVehicleIsDissociated() {
        driver.associateVehicle(vehicle);

        driver.dissociateVehicle(vehicle);

        verify(vehicle).dissociateDriver(driver);
    }

    @Test(expected = InvalidVehicleAssociationException.class)
    public void givenADriverWithNoVehicleAssociated_whenDissociatingVehicle_thenThrowsInvalidAssociationException() {
        driver.dissociateVehicle(vehicle);
    }

    @Test(expected = InvalidVehicleAssociationException.class)
    public void givenNullVehicle_whenAssociatingVehicle_thenThrowsInvalidVehicleAssociatinException() {
        driver.associateVehicle(vehicle);

        driver.dissociateVehicle(null);
    }
}