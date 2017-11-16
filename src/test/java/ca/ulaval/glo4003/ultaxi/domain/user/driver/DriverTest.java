package ca.ulaval.glo4003.ultaxi.domain.user.driver;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;

import ca.ulaval.glo4003.ultaxi.domain.transportrequest.TransportRequest;
import ca.ulaval.glo4003.ultaxi.domain.transportrequest.exception.InvalidTransportRequestAssignationException;
import ca.ulaval.glo4003.ultaxi.domain.user.Role;
import ca.ulaval.glo4003.ultaxi.domain.user.User;
import ca.ulaval.glo4003.ultaxi.domain.user.exception.InvalidSocialInsuranceNumberException;
import ca.ulaval.glo4003.ultaxi.domain.vehicle.Vehicle;
import ca.ulaval.glo4003.ultaxi.domain.vehicle.exception.InvalidVehicleAssociationException;
import ca.ulaval.glo4003.ultaxi.domain.vehicle.exception.InvalidVehicleDissociationException;
import ca.ulaval.glo4003.ultaxi.domain.vehicle.VehicleType;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DriverTest {

    private Driver driver;
    @Mock
    private Vehicle vehicle;

    @Mock
    TransportRequest transportRequest;

    @Before
    public void setUp() {
        driver = new Driver();
        transportRequest = new TransportRequest();
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
    public void
    givenADriverWithAVehicleAssociated_whenAssociatingVehicle_thenThrowsInvalidVehicleAssociationException() {
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

        driver.dissociateVehicle();

        verify(vehicle).dissociateDriver();
    }

    @Test(expected = InvalidVehicleDissociationException.class)
    public void givenADriverWithNoVehicleAssociated_whenDissociatingVehicle_thenThrowsInvalidAssociationException() {
        driver.dissociateVehicle();
    }

    @Test
    public void givenValidTransportRequest_whenAssignTransportRequest_thenNoExceptionIsThrown() {
        driver.assignTransportRequest(transportRequest);
    }

    @Test(expected = InvalidTransportRequestAssignationException.class)
    public void givenUnavailableTransportRequest_whenAssignTransportRequest_thenExceptionIsThrown() {
        transportRequest.setUnavailable();
        driver.assignTransportRequest(transportRequest);
    }

    @Test(expected = InvalidTransportRequestAssignationException.class)
    public void givenDriverWithTransportRequest_whenAssignTransportRequest_thenExceptionIsThrown() {
        driver.setTransportRequest(transportRequest);
        driver.assignTransportRequest(transportRequest);
    }

    @Test(expected = InvalidTransportRequestAssignationException.class)
    public void givenInvalidDriverVehiculeType_whenAssignTransportRequest_thenExceptionIsThrown() {
        driver.setVehicleType(VehicleType.VAN);
        driver.assignTransportRequest(transportRequest);
    }
}