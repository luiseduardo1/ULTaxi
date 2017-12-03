package ca.ulaval.glo4003.ultaxi.domain.user.driver;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;

import ca.ulaval.glo4003.ultaxi.domain.transportrequest.TransportRequest;
import ca.ulaval.glo4003.ultaxi.domain.transportrequest.exception.DriverHasNoTransportRequestAssignedException;
import ca.ulaval.glo4003.ultaxi.domain.transportrequest.exception.InvalidTransportRequestAssignationException;
import ca.ulaval.glo4003.ultaxi.domain.user.PhoneNumber;
import ca.ulaval.glo4003.ultaxi.domain.user.Role;
import ca.ulaval.glo4003.ultaxi.domain.user.SocialInsuranceNumber;
import ca.ulaval.glo4003.ultaxi.domain.vehicle.Van;
import ca.ulaval.glo4003.ultaxi.domain.vehicle.Vehicle;
import ca.ulaval.glo4003.ultaxi.domain.vehicle.exception.InvalidVehicleAssociationException;
import ca.ulaval.glo4003.ultaxi.domain.vehicle.exception.InvalidVehicleDissociationException;
import ca.ulaval.glo4003.ultaxi.utils.hashing.HashingStrategy;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DriverTest {

    private static final String A_USERNAME = "JeffSmith32";
    private static final String A_PASSWORD = "mystrongpassword";
    private static final String A_PHONE_NUMBER = "234-235-5678";
    private static final String AN_EMAIL_ADDRESS = "jeff.smith.32@ulaval.ca";
    private static final String A_FIRST_NAME = "Jeff";
    private static final String A_SECOND_NAME = "Smith";
    private static final String A_VALID_SOCIAL_INSURANCE_NUMBER = "972487086";


    private static final String A_HASH = RandomStringUtils.randomAlphabetic(10);

    @Mock
    private HashingStrategy hashingStrategy;
    @Mock
    private Vehicle vehicle;
    @Mock
    private Van van;

    private Driver driver;
    private TransportRequest transportRequest;
    private PhoneNumber phoneNumber = new PhoneNumber(A_PHONE_NUMBER);
    private SocialInsuranceNumber socialInsuranceNumber = new SocialInsuranceNumber(A_VALID_SOCIAL_INSURANCE_NUMBER);

    @Before
    public void setUp() {
        willReturn(A_HASH).given(hashingStrategy).hashWithRandomSalt(anyString());

        driver = new Driver(A_USERNAME, A_PASSWORD, phoneNumber, AN_EMAIL_ADDRESS, hashingStrategy, A_FIRST_NAME,
                            A_SECOND_NAME, socialInsuranceNumber);
        transportRequest = new TransportRequest();
    }

    @Test
    public void givenValidSocialInsuranceNumber_whenSetSocialInsuranceNumber_thenSocialInsuranceNumberIsAssigned() {
        driver.setSocialInsuranceNumber(socialInsuranceNumber);

        Assert.assertEquals(driver.getSocialInsuranceNumber(), socialInsuranceNumber);
    }

    @Test
    public void
    givenANewDriverUser_whenCreateDriver_thenRoleShouldBeADriver() {
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

    @Test(expected = DriverHasNoTransportRequestAssignedException.class)
    public void givenADriverWithNoTransportRequestAssigned_whenGetCurrentTransportRequestId_thenThrowsException() {
        driver.getCurrentTransportRequestId();
    }

    @Test
    public void givenValidTransportRequest_whenAssignTransportRequest_thenNoExceptionIsThrown() {
        driver.associateVehicle(vehicle);

        driver.assignTransportRequestId(transportRequest);
    }

    @Test(expected = InvalidTransportRequestAssignationException.class)
    public void givenUnavailableTransportRequest_whenAssignTransportRequest_thenExceptionIsThrown() {
        transportRequest.setUnavailable();
        driver.assignTransportRequestId(transportRequest);
    }

    @Test(expected = InvalidTransportRequestAssignationException.class)
    public void givenDriverWithTransportRequest_whenAssignTransportRequest_thenExceptionIsThrown() {
        driver.assignTransportRequestId(transportRequest);
        driver.assignTransportRequestId(transportRequest);
    }

    @Test(expected = InvalidTransportRequestAssignationException.class)
    public void givenInvalidDriverVehicleType_whenAssignTransportRequestId_thenExceptionIsThrown() {
        transportRequest.setVehicleType("car");
        driver.associateVehicle(van);
        driver.assignTransportRequestId(transportRequest);
    }
}