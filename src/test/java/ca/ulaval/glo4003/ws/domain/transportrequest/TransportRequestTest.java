package ca.ulaval.glo4003.ws.domain.transportrequest;

import ca.ulaval.glo4003.ws.domain.vehicle.InvalidVehicleTypeException;
import org.junit.Before;
import org.junit.Test;

public class TransportRequestTest {

    private static final String AN_INVALID_VEHICLE_TYPE = "Invalid";

    private TransportRequest transportRequest;

    @Before
    public void setUp() throws Exception {
        transportRequest = new TransportRequest();
    }

    @Test(expected = InvalidVehicleTypeException.class)
    public void givenAnInvalidVehicleType_whenSetVehicleType_thenThrowsException() {
        transportRequest.setVehicleType(AN_INVALID_VEHICLE_TYPE);
    }
}
