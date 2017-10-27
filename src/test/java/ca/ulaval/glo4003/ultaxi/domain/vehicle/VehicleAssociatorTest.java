package ca.ulaval.glo4003.ultaxi.domain.vehicle;

import ca.ulaval.glo4003.ultaxi.domain.user.Role;
import ca.ulaval.glo4003.ultaxi.domain.user.User;
import ca.ulaval.glo4003.ultaxi.domain.user.driver.Driver;
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
        this.user = new User();
    }

    @Test
    public void givenValidUserAndValidVehicle_WhenAssociating_thenNoExceptionIsThrown() {
        user.setRole(Role.DRIVER);
        vehicleAssociator.associate(vehicle, user);

    }
}
