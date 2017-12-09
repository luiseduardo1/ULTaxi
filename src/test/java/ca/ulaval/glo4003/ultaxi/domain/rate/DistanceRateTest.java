package ca.ulaval.glo4003.ultaxi.domain.rate;

import ca.ulaval.glo4003.ultaxi.domain.rate.exception.InvalidRateException;
import ca.ulaval.glo4003.ultaxi.domain.vehicle.exception.InvalidVehicleTypeException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;

@RunWith(MockitoJUnitRunner.class)
public class DistanceRateTest {

    private static final BigDecimal A_VALID_RATE = BigDecimal.TEN;
    private static final String A_INVALID_VEHICLE_TYPE = "voiturette";
    private static final BigDecimal A_INVALID_RATE = BigDecimal.ZERO;

    private DistanceRate distanceRate;

    @Before
    public void setUp() {
        distanceRate = new DistanceRate();
    }

    @Test (expected = InvalidRateException.class)
    public void givenRateLessThanZero_whenAssigningRate_shouldThrowException() {
        distanceRate.setRate(A_INVALID_RATE);
    }

    @Test
    public void givenRateGreaterThanZero_whenAssigningRate_shouldAssignRate() {
        distanceRate.setRate(A_VALID_RATE);
    }

    @Test (expected = InvalidVehicleTypeException.class)
    public void givenInvalidVehiculeType_whenAssigningVehicleType_shouldThrowException() {
        distanceRate.setVehicleType(A_INVALID_VEHICLE_TYPE);
    }
}