package ca.ulaval.glo4003.ultaxi.infrastructure.rate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.BDDMockito.willReturn;

import ca.ulaval.glo4003.ultaxi.domain.rate.DistanceRate;
import ca.ulaval.glo4003.ultaxi.domain.rate.RateRepository;
import ca.ulaval.glo4003.ultaxi.domain.rate.RateType;
import ca.ulaval.glo4003.ultaxi.domain.rate.exception.NonExistentRateException;
import ca.ulaval.glo4003.ultaxi.domain.rate.exception.RateAlreadyExistsException;
import ca.ulaval.glo4003.ultaxi.domain.vehicle.VehicleType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;

@RunWith(MockitoJUnitRunner.class)
public class RateRepositoryInMemoryTest {

    private static final BigDecimal A_RATE = BigDecimal.TEN;
    private static final RateType A_RATE_TYPE = RateType.DISTANCE;
    private static final VehicleType A_VEHICLE_TYPE = VehicleType.CAR;

    @Mock
    DistanceRate distanceRate;

    private RateRepository rateRepository;

    @Before
    public void setUp() {
        willReturn(A_RATE).given(distanceRate).getRate();
        willReturn(A_RATE_TYPE).given(distanceRate).getRateType();
        willReturn(A_VEHICLE_TYPE).given(distanceRate).getVehicleType();
        rateRepository = new RateRepositoryInMemory();
    }

    @Test
    public void givenDistanceRate_whenSave_thenUserHasSameParameters() {
        rateRepository.save(distanceRate);
        DistanceRate savedRate = rateRepository.findDistanceRateByVehicleType(distanceRate.getVehicleType());

        assertEquals(distanceRate, savedRate);
    }

    @Test
    public void givenNonExistentDistanceRate_whenFindingRateByVehiculeType_thenReturnsNull() {
        DistanceRate savedRate = rateRepository.findDistanceRateByVehicleType(distanceRate.getVehicleType());

        assertNull(savedRate);
    }

    @Test(expected = NonExistentRateException.class)
    public void givenNonExistentDistanceRate_whenUpdate_thenThrowsException() {
        rateRepository.update(distanceRate);
    }

    @Test(expected = RateAlreadyExistsException.class)
    public void givenExistingDistanceRate_whenSave_thenThrowsException() {
        rateRepository.save(distanceRate);
        rateRepository.save(distanceRate);
    }

    @Test
    public void givenDistanceRateToUpdate_whenUpdate_thenNoExceptionIsThrown() {
        rateRepository.save(distanceRate);
        rateRepository.update(distanceRate);
    }

    @Test
    public void givenExistingDistanceRate_whenUpdate_thenDistanceRateHasUpdatedParameters() {
        distanceRate.setRate(A_RATE);
        distanceRate.setRateType(A_RATE_TYPE);
        distanceRate.setVehicleType(A_VEHICLE_TYPE);
        DistanceRate differentDistanceRate = distanceRate;
        differentDistanceRate.setRate(BigDecimal.ONE);

        rateRepository.save(distanceRate);
        rateRepository.update(differentDistanceRate);

        DistanceRate updatedDistanceRate = rateRepository.findDistanceRateByVehicleType(distanceRate.getVehicleType());
        assertEquals(distanceRate.getVehicleType(), differentDistanceRate.getVehicleType());
        assertEquals(differentDistanceRate.getRate(), updatedDistanceRate.getRate());
    }
}