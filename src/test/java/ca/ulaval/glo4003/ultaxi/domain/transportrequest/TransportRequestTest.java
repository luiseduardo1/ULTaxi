package ca.ulaval.glo4003.ultaxi.domain.transportrequest;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.willReturn;

import ca.ulaval.glo4003.ultaxi.domain.transportrequest.exception.InvalidTransportRequestCompletionException;
import ca.ulaval.glo4003.ultaxi.domain.transportrequest.exception.InvalidTransportRequestStatusException;
import ca.ulaval.glo4003.ultaxi.domain.user.driver.Driver;
import ca.ulaval.glo4003.ultaxi.domain.vehicle.exception.InvalidVehicleTypeException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TransportRequestTest {

    private static final String AN_INVALID_VEHICLE_TYPE = "Invalid";
    private static final TransportRequestStatus PENDING_STATUS = TransportRequestStatus.PENDING;

    @Mock
    Driver driver;

    private TransportRequest transportRequest;

    @Before
    public void setUp() {
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

    @Test
    public void givenADriverWithTheSameTransportRequest_whenComplete_thenTransportRequestIsCompleted(){
        willReturn(transportRequest.getId()).given(driver).getCurrentTransportRequestId();

        transportRequest.complete(driver);

        assertEquals(TransportRequestStatus.COMPLETED, transportRequest.getTransportRequestStatus());
    }

    @Test(expected = InvalidTransportRequestCompletionException.class)
    public void givenADriverWithADifferentTransportRequest_whenComplete_thenThrowsException(){
        transportRequest.complete(driver);
    }
}
