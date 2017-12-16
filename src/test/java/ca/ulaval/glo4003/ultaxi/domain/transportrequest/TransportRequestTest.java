package ca.ulaval.glo4003.ultaxi.domain.transportrequest;

import ca.ulaval.glo4003.ultaxi.domain.geolocation.Geolocation;
import ca.ulaval.glo4003.ultaxi.domain.rate.Rate;
import ca.ulaval.glo4003.ultaxi.domain.transportrequest.exception.InvalidTransportRequestCompletionException;
import ca.ulaval.glo4003.ultaxi.domain.transportrequest.exception.InvalidTransportRequestStatusException;
import ca.ulaval.glo4003.ultaxi.domain.user.driver.Driver;
import ca.ulaval.glo4003.ultaxi.domain.vehicle.exception.InvalidVehicleTypeException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class TransportRequestTest {

    private static final String AN_INVALID_VEHICLE_TYPE = "Invalid";
    private static final TransportRequestStatus PENDING_STATUS = TransportRequestStatus.PENDING;
    private static final Geolocation A_VALID_STARTING_GEOLOCATION= new Geolocation(46.776635, -71.270671);
    private static final Geolocation A_VALID_ENDING_GEOLOCATION = new Geolocation(46.8083722, -71.2196447);
    @Mock
    Driver driver;
    @Mock
    Rate rate;

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
    public void givenADriverWithTheSameTransportRequest_whenComplete_thenTransportRequestIsCompleted() {
        transportRequest.setStartingPosition(A_VALID_STARTING_GEOLOCATION);
        willReturn(transportRequest.getId()).given(driver).getCurrentTransportRequestId();

        transportRequest.setToCompleted(driver, A_VALID_ENDING_GEOLOCATION);

        assertEquals(TransportRequestStatus.COMPLETED, transportRequest.getTransportRequestStatus());
    }

    @Test(expected = InvalidTransportRequestCompletionException.class)
    public void givenADriverWithADifferentTransportRequest_whenComplete_thenThrowsException() {
        transportRequest.setToCompleted(driver, A_VALID_ENDING_GEOLOCATION);
    }

    @Test
    public void givenAValidRateAndEndingPosition_whenCalculateTotalAmount_thenDelegateToRate() {
        transportRequest.calculateTotalAmount(rate);

        verify(rate).calculateTotalAmount(transportRequest.getStartingPosition(),transportRequest.getEndingPosition());
    }
}
