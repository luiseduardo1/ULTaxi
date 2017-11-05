package ca.ulaval.glo4003.ultaxi.domain.vehicle;

import ca.ulaval.glo4003.ultaxi.domain.user.Role;
import ca.ulaval.glo4003.ultaxi.domain.user.User;
import ca.ulaval.glo4003.ultaxi.domain.user.driver.Driver;
import ca.ulaval.glo4003.ultaxi.domain.vehicle.exception.InvalidVehicleAssociationException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class VehicleAssociatorTest {

    private VehicleAssociator vehicleAssociator;
    public User user;
    public Vehicle vehicle;

    @Before
    public void setUp() {
        vehicleAssociator = new VehicleAssociator();
        this.user = new Driver();
        this.vehicle = new Car("red", "porshe", "12");
    }

    @Test
    public void givenValidUserAndValidVehicle_WhenAssociating_thenNoExceptionIsThrown() {
        user.setRole(Role.DRIVER);
        vehicleAssociator.associate(vehicle, user);
    }

    @Test(expected = InvalidVehicleAssociationException.class)
    public void givenValidUserWithAnInvalidRole_WhenAssociating_thenExceptionIsThrown() {
        user.setRole(null);
        vehicleAssociator.associate(vehicle, user);
    }

    @Test(expected = InvalidVehicleAssociationException.class)
    public void givenValidUserWithAnInvalidVehicle_WhenAssociating_thenExceptionIsThrown() {
        user.setRole(Role.DRIVER);
        vehicle.setDriver((Driver) user);
        vehicleAssociator.associate(vehicle, user);
    }

    @Test(expected = InvalidVehicleAssociationException.class)
    public void givenInValidUserWithAnInvalidVehicle_WhenAssociating_thenExceptionIsThrown() {
        user.setRole(Role.DRIVER);
        ((Driver) user).setVehicle(vehicle);
        vehicleAssociator.associate(vehicle, user);
    }

    @Test
    public void givenValidUserAndValidVehicle_WhenDissociating_thenNoExceptionIsThrown() {
        user.setRole(Role.DRIVER);
        vehicle.setDriver((Driver) user);
        ((Driver) user).setVehicle(vehicle);
        vehicleAssociator.dissociate(vehicle, user);
    }

    @Test(expected = InvalidVehicleAssociationException.class)
    public void givenValidUserWithAnInvalidRole_WhenDissociating_thenExceptionIsThrown() {
        vehicleAssociator.dissociate(vehicle, user);
    }

    @Test(expected = InvalidVehicleAssociationException.class)
    public void givenValidUserWithAnInvalidVehicle_WhenDissociating_thenExceptionIsThrown() {
        user.setRole(Role.DRIVER);
        ((Driver) user).setVehicle(vehicle);
        vehicleAssociator.dissociate(vehicle, user);
    }

    @Test(expected = InvalidVehicleAssociationException.class)
    public void givenInValidUserWithAValidVehicle_WhenDissociating_thenExceptionIsThrown() {
        user.setRole(Role.DRIVER);
        vehicle.setDriver((Driver) user);
        vehicleAssociator.associate(vehicle, user);
    }

}
