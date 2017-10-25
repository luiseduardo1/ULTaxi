package ca.ulaval.glo4003.ultaxi.domain.transportrequest;

import static org.junit.Assert.assertEquals;

import ca.ulaval.glo4003.ultaxi.domain.vehicle.exception.InvalidVehicleTypeException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runners.JUnit4;
import org.mockito.BDDMockito;
import org.mockito.MockitoAnnotations;

public class TransportRequestTest {

    private static final String AN_INVALID_VEHICLE_TYPE = "Invalid";
    private static final TransportRequestStatus PENDING_STATUS = TransportRequestStatus.Pending;

    private TransportRequest transportRequest;

    @Before
    public void setUp() throws Exception {
        transportRequest = new TransportRequest();
    }

    @Test(expected = InvalidVehicleTypeException.class)
    public void givenAnInvalidVehicleType_whenSetVehicleType_thenThrowsException() {
        transportRequest.setVehicleType(AN_INVALID_VEHICLE_TYPE);
    }

    @Test
    public void givenATransportRequest_whenCreated_thenTransportRequestStatusIsPending() {
        TransportRequest aNewTransportRequest = new TransportRequest();

        assertEquals(PENDING_STATUS, aNewTransportRequest.getTransportRequestStatus());
    }
}
