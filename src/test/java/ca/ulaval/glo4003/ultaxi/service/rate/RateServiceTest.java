package ca.ulaval.glo4003.ultaxi.service.rate;

import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.verify;

import ca.ulaval.glo4003.ultaxi.domain.rate.DistanceRate;
import ca.ulaval.glo4003.ultaxi.domain.rate.RateRepository;
import ca.ulaval.glo4003.ultaxi.domain.vehicle.VehicleType;
import ca.ulaval.glo4003.ultaxi.transfer.rate.DistanceRateAssembler;
import ca.ulaval.glo4003.ultaxi.transfer.rate.DistanceRateDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;

@RunWith(MockitoJUnitRunner.class)
public class RateServiceTest {

    private final static BigDecimal A_VALID_RATE = BigDecimal.TEN;
    private final static VehicleType A_VALID_VEHICLE_TYPE = VehicleType.CAR;

    @Mock
    private DistanceRate distanceRate;
    @Mock
    private DistanceRateDto distanceRateDto;
    @Mock
    private RateRepository rateRepository;
    @Mock
    private DistanceRateAssembler distanceRateAssembler;

    private RateService rateService;

    @Before
    public void setUp() {
        willReturn(distanceRate).given(distanceRateAssembler).create(distanceRateDto);
        willReturn(A_VALID_RATE).given(distanceRate).getRate();
        willReturn(A_VALID_VEHICLE_TYPE).given(distanceRate).getVehicleType();
        rateService = new RateService(rateRepository, distanceRateAssembler);
    }

    @Test
    public void givenDistanceRate_whenAddDistanceRate_thenRateIsAdded() {
        rateService.addDistanceRate(distanceRateDto);

        verify(rateRepository).save(distanceRate);
    }

    @Test
    public void givenDistanceRateUpdate_whenUpdateDistanceRate_thenDistanceRateIsUpdated() {
        rateService.updateDistanceRate(distanceRateDto);

        verify(rateRepository).update(distanceRate);
    }

}