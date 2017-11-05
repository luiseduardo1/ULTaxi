package ca.ulaval.glo4003.ultaxi.domain.transportrequest;

import ca.ulaval.glo4003.ultaxi.domain.transportrequest.exception.InvalidTransportRequestAssignationException;
import ca.ulaval.glo4003.ultaxi.domain.user.Role;
import ca.ulaval.glo4003.ultaxi.domain.user.User;
import ca.ulaval.glo4003.ultaxi.domain.user.driver.Driver;
import ca.ulaval.glo4003.ultaxi.domain.vehicle.VehicleType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TransportRequestAssignatorTest {

    private static final String A_VALID_STRING_VEHICULE_TYPE_CAR = "car";

    private TransportRequestAssignator transportRequestAssignator;
    public User user;
    public TransportRequest transportRequest;

    @Before
    public void setUp() {
        transportRequestAssignator = new TransportRequestAssignator();
        this.user = new Driver();
        this.transportRequest = new TransportRequest();
        ((Driver) user).setVehicleType(VehicleType.CAR);
        transportRequest.setVehicleType(A_VALID_STRING_VEHICULE_TYPE_CAR);
    }

    @Test
    public void givenValidUserAndValidTransportRequest_whenAssignToDriver_thenNoExceptionIsThrown() {
        user.setRole(Role.DRIVER);
        transportRequestAssignator.assignToDriver(transportRequest, user);
    }

    @Test(expected = InvalidTransportRequestAssignationException.class)
    public void givenValidUserWithAnInvalidRole_whenAssignToDriver_thenExceptionIsThrown() {
        user.setRole(null);
        transportRequestAssignator.assignToDriver(transportRequest, user);
    }

    @Test(expected = InvalidTransportRequestAssignationException.class)
    public void givenValidUserWithAUnavailableTransportRequest_whenAssignToDriver_thenExceptionIsThrown() {
        user.setRole(Role.DRIVER);
        transportRequest.setUnavailable();
        transportRequestAssignator.assignToDriver(transportRequest, user);
    }

    @Test(expected = InvalidTransportRequestAssignationException.class)
    public void givenInvalidUserWithValidTransportRequest_whenAssignToDriver_thenExceptionIsThrown() {
        user.setRole(Role.DRIVER);
        ((Driver) user).setTransportRequest(transportRequest);
        transportRequestAssignator.assignToDriver(transportRequest, user);
    }

    @Test(expected = InvalidTransportRequestAssignationException.class)
    public void givenInvalidDriverVehiculeType_whenAssignToDriver_thenExceptionIsThrown() {
        user.setRole(Role.DRIVER);
        ((Driver) user).setVehicleType(VehicleType.VAN);
        transportRequestAssignator.assignToDriver(transportRequest, user);
    }
}
