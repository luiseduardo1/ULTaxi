package ca.ulaval.glo4003.ultaxi.domain.vehicle;

import ca.ulaval.glo4003.ultaxi.domain.user.driver.Driver;
import ca.ulaval.glo4003.ultaxi.domain.vehicle.exception.InvalidVehicleAssociationException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class VehicleTest {

    @Mock
    private Driver driver;
    private Vehicle vehicle;

    @Before
    public void setUp() {
        vehicle = new Vehicle(null, null, null) {
            @Override
            public VehicleType getType() {
                return null;
            }
        };
    }

    @Test
    public void givenVehicleWithNoDriverAssociated_whenAssociatingDriver_thenDriverIsAssociated() {
        vehicle.associateDriver(driver);
    }

    @Test(expected = InvalidVehicleAssociationException.class)
    public void givenVehicleWithADriverAssociated_whenAssociatingDriver_thenThrowsInvalidVehicleAssociationException() {
        vehicle.associateDriver(driver);

        vehicle.associateDriver(driver);
    }

    @Test(expected = InvalidVehicleAssociationException.class)
    public void givenNullDriver_whenAssociatingDriver_thenThrowsInvalidVehicleAssociationException() {
        vehicle.associateDriver(null);
    }

    @Test(expected = InvalidVehicleAssociationException.class)
    public void
    givenAVehicleWithNoAssociatedDriver_whenDissociatingDriver_thenThrowsInvalidVehicleAssociationException() {
        vehicle.dissociateDriver(driver);
    }

    @Test(expected = InvalidVehicleAssociationException.class)
    public void givenNullDriver_whenDissociatingDriver_thenThrowsInvalidVehicleAssociationException() {
        vehicle.dissociateDriver(null);
    }

    @Test
    public void givenAVehicleWithAnAssociatedDriver_whenDissociatingDriver_thenDriverIsDissociated() {
        vehicle.associateDriver(driver);

        vehicle.dissociateDriver(driver);
    }
}