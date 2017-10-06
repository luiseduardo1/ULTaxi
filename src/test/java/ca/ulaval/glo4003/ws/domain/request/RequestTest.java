package ca.ulaval.glo4003.ws.domain.request;

import ca.ulaval.glo4003.ws.domain.vehicle.InvalidVehicleTypeException;
import org.junit.Before;
import org.junit.Test;

public class RequestTest {

    private static final String AN_INVALID_VEHICLE_TYPE = "Invalid";

    private Request request;

    @Before
    public void setUp() throws Exception {
        request = new Request();
    }

    @Test(expected = InvalidVehicleTypeException.class)
    public void givenAnInvalideVehicleType_whenSetVehicleType_thenThrowsException() {
        request.setVehicleType(AN_INVALID_VEHICLE_TYPE);
    }
}
