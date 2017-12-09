package ca.ulaval.glo4003.ultaxi.domain.transportrequest;

import ca.ulaval.glo4003.ultaxi.domain.geolocation.Geolocation;
import ca.ulaval.glo4003.ultaxi.domain.money.Money;
import ca.ulaval.glo4003.ultaxi.domain.rate.Rate;
import ca.ulaval.glo4003.ultaxi.domain.transportrequest.exception.InvalidTransportRequestCompletionException;
import ca.ulaval.glo4003.ultaxi.domain.transportrequest.exception.InvalidTransportRequestStatusException;
import ca.ulaval.glo4003.ultaxi.domain.user.driver.Driver;
import ca.ulaval.glo4003.ultaxi.domain.vehicle.exception.InvalidVehicleTypeException;
import ca.ulaval.glo4003.ultaxi.utils.distanceCalculator.DistanceCalculatorStrategy;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.willReturn;

@RunWith(MockitoJUnitRunner.class)
public class TransportRequestTest {

    private static final String AN_INVALID_VEHICLE_TYPE = "Invalid";
    private static final TransportRequestStatus PENDING_STATUS = TransportRequestStatus.PENDING;
    private static final double A_VALID_DISTANCE = 5;
    private static final BigDecimal A_VALID_RATE = new BigDecimal(10);
    private static final Geolocation A_VALID_STARTING_GEOLOCATION= new Geolocation(46.776635, -71.270671);
    private static final Geolocation A_VALID_ENDING_GEOLOCATION = new Geolocation(46.8083722, -71.2196447);
    @Mock
    Driver driver;
    @Mock
    Rate rate;
    @Mock
    DistanceCalculatorStrategy distanceCalculatorStrategy;
    @Mock
    Geolocation endingPosition;

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
        willReturn(transportRequest.getId()).given(driver).getCurrentTransportRequestId();

        transportRequest.complete(driver);

        assertEquals(TransportRequestStatus.COMPLETED, transportRequest.getTransportRequestStatus());
    }

    @Test(expected = InvalidTransportRequestCompletionException.class)
    public void givenADriverWithADifferentTransportRequest_whenComplete_thenThrowsException() {
        transportRequest.complete(driver);
    }

    @Test
    public void givenAValidRateAndEndingPosition_whenCalculateTotalAmount_thenTransportRequestHaveTotalAmount() {
        transportRequest.setStartingPosition(A_VALID_STARTING_GEOLOCATION);
        willReturn(A_VALID_RATE).given(rate).getValue();
        willReturn(A_VALID_DISTANCE).given(distanceCalculatorStrategy).calculDistance(transportRequest.getStartingPosition().getLatitude(),
                transportRequest.getStartingPosition().getLongitude(), A_VALID_ENDING_GEOLOCATION.getLatitude(),
                A_VALID_ENDING_GEOLOCATION.getLongitude());

        Money totalAmount = transportRequest.calculateTotalAmount(rate, A_VALID_ENDING_GEOLOCATION, distanceCalculatorStrategy);

        BigDecimal supposedTotalAmount = BigDecimal.valueOf(A_VALID_DISTANCE).multiply(A_VALID_RATE);
        assertEquals(supposedTotalAmount, totalAmount.getValue());
    }
}
