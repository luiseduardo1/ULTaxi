package ca.ulaval.glo4003.ultaxi.infrastructure.rate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.BDDMockito.willReturn;

import ca.ulaval.glo4003.ultaxi.domain.rate.RateRepository;
import ca.ulaval.glo4003.ultaxi.domain.rate.RateType;
import ca.ulaval.glo4003.ultaxi.domain.rate.exception.NonExistentRateException;
import ca.ulaval.glo4003.ultaxi.domain.rate.exception.RateAlreadyExistsException;
import ca.ulaval.glo4003.ultaxi.domain.vehicle.VehicleType;
import ca.ulaval.glo4003.ultaxi.transfer.rate.RatePersistenceDto;
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
    RatePersistenceDto ratePersistenceDistanceDto;

    private RateRepository rateRepository;

    @Before
    public void setUp() {
        willReturn(A_RATE).given(ratePersistenceDistanceDto).getValue();
        willReturn(A_RATE_TYPE).given(ratePersistenceDistanceDto).getRateType();
        willReturn(A_VEHICLE_TYPE).given(ratePersistenceDistanceDto).getVehicleType();
        rateRepository = new RateRepositoryInMemory();
    }

    @Test
    public void givenDistanceRate_whenSave_thenUserHasSameParameters() {
        rateRepository.save(ratePersistenceDistanceDto);
        RatePersistenceDto savedRatePersistenceDto
            = rateRepository.findDistanceRateByVehicleType(ratePersistenceDistanceDto.getVehicleType());

        assertEquals(ratePersistenceDistanceDto, savedRatePersistenceDto);
    }

    @Test
    public void givenNonExistentDistanceRate_whenFindingRateByVehiculeType_thenReturnsNull() {
        RatePersistenceDto savedRatePersistenceDto = rateRepository.findDistanceRateByVehicleType(ratePersistenceDistanceDto.getVehicleType());

        assertNull(savedRatePersistenceDto);
    }

    @Test(expected = NonExistentRateException.class)
    public void givenNonExistentDistanceRate_whenUpdate_thenThrowsException() {
        rateRepository.update(ratePersistenceDistanceDto);
    }

    @Test(expected = RateAlreadyExistsException.class)
    public void givenExistingDistanceRate_whenSave_thenThrowsException() {
        rateRepository.save(ratePersistenceDistanceDto);
        rateRepository.save(ratePersistenceDistanceDto);
    }

    @Test
    public void givenDistanceRateToUpdate_whenUpdate_thenNoExceptionIsThrown() {
        rateRepository.save(ratePersistenceDistanceDto);
        rateRepository.update(ratePersistenceDistanceDto);
    }

    @Test
    public void givenExistingDistanceRate_whenUpdate_thenDistanceRateHasUpdatedParameters() {
        ratePersistenceDistanceDto.setValue(A_RATE);
        ratePersistenceDistanceDto.setRateType(A_RATE_TYPE);
        ratePersistenceDistanceDto.setVehicleType(A_VEHICLE_TYPE);
        RatePersistenceDto differentDistanceRatePersistenceDto = ratePersistenceDistanceDto;
        differentDistanceRatePersistenceDto.setValue(BigDecimal.ONE);

        rateRepository.save(ratePersistenceDistanceDto);
        rateRepository.update(differentDistanceRatePersistenceDto);

        RatePersistenceDto updatedDistanceRate =
            rateRepository.findDistanceRateByVehicleType(ratePersistenceDistanceDto.getVehicleType());
        assertEquals(ratePersistenceDistanceDto.getVehicleType(), differentDistanceRatePersistenceDto.getVehicleType());
        assertEquals(differentDistanceRatePersistenceDto.getValue(), updatedDistanceRate.getValue());
    }
}