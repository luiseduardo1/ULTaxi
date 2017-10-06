package ca.ulaval.glo4003.ws.domain.vehicle;

import static org.junit.Assert.assertTrue;

import ca.ulaval.glo4003.ws.domain.vehicle.exception.InvalidVehicleTypeException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class VehicleFactoryTest {

    private static final String NULL_VEHICLE_TYPE = null;
    private static final String INVALID_VEHICLE_TYPE = "Truck";
    private static final String CAR_VEHICLE_TYPE = "Car";
    private static final String VAN_VEHICLE_TYPE = "Van";
    private static final String LIMOUSINE_VEHICLE_TYPE = "Limousine";

    private static final String COLOR = "A color";
    private static final String MODEL = "A model";
    private static final String REGISTRATION_NUMBER = "T33333";

    @Test(expected = InvalidVehicleTypeException.class)
    public void givenNullVehicleType_whenGetVehicle_thenThrowsException() {
        getVehicle(NULL_VEHICLE_TYPE);
    }

    @Test(expected = InvalidVehicleTypeException.class)
    public void givenInvalidVehicleType_whenGetVehicle_thenThrowsException() {
        getVehicle(INVALID_VEHICLE_TYPE);
    }

    @Test
    public void givenCarVehicleType_whenGetVehicle_thenReturnsCar() {
        Vehicle returnedVehicle = getVehicle(CAR_VEHICLE_TYPE);

        assertTrue(returnedVehicle instanceof Car);
    }

    @Test
    public void givenVanVehicleType_whenGetVehicle_thenReturnsVan() {
        Vehicle returnedVehicle = getVehicle(VAN_VEHICLE_TYPE);

        assertTrue(returnedVehicle instanceof Van);
    }

    @Test
    public void givenLimousineVehicleType_whenGetVehicle_thenReturnsLimousine() {
        Vehicle returnedVehicle = getVehicle(LIMOUSINE_VEHICLE_TYPE);

        assertTrue(returnedVehicle instanceof Limousine);
    }

    private Vehicle getVehicle(String vehicleType) {
        return VehicleFactory.getVehicle(vehicleType, COLOR, MODEL, REGISTRATION_NUMBER);
    }
}