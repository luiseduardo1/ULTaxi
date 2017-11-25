package ca.ulaval.glo4003.ultaxi.domain.transportrequest;

import static org.junit.Assert.assertEquals;

import ca.ulaval.glo4003.ultaxi.domain.transportrequest.exception.InvalidTransportRequestStatusException;
import ca.ulaval.glo4003.ultaxi.domain.vehicle.exception.InvalidVehicleTypeException;
import org.junit.Before;
import org.junit.Test;


public class TransportRequestTest {

    private static final String AN_INVALID_VEHICLE_TYPE = "Invalid";
    private static final TransportRequestStatus PENDING_STATUS = TransportRequestStatus.PENDING;

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

    @Test(expected = InvalidTransportRequestStatusException.class)
    public void givenATransportRequestWithArrivedStatus_whenUpdatingStatusToArrived_thenThrowsException() {
        transportRequest.updateStatus(TransportRequestStatus.ARRIVED);
        transportRequest.updateStatus(TransportRequestStatus.ARRIVED);
    }

    @Test
    public void givenATransportRequest_whenCreated_thenTransportRequestIsAvailable() {
        TransportRequest aNewTransportRequest = new TransportRequest();

        assertEquals(true, aNewTransportRequest.isAvailable());
    }
}
