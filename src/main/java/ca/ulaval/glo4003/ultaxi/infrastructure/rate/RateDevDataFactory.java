package ca.ulaval.glo4003.ultaxi.infrastructure.rate;

import ca.ulaval.glo4003.ultaxi.domain.rate.DistanceRate;
import ca.ulaval.glo4003.ultaxi.domain.rate.Rate;
import ca.ulaval.glo4003.ultaxi.domain.rate.RateFactory;
import ca.ulaval.glo4003.ultaxi.domain.vehicle.VehicleType;
import ca.ulaval.glo4003.ultaxi.transfer.rate.RatePersistenceDto;
import jersey.repackaged.com.google.common.collect.Lists;

import java.math.BigDecimal;
import java.util.List;

public class RateDevDataFactory {
    private static final BigDecimal A_VALID_RATE = BigDecimal.TEN;

    public List<RatePersistenceDto> createMockData() {

        RatePersistenceDto limousineRatePersistenceDto = new RatePersistenceDto();
        limousineRatePersistenceDto.setValue(A_VALID_RATE);
        limousineRatePersistenceDto.setVehicleType(VehicleType.LIMOUSINE);

        List<RatePersistenceDto> rates = Lists.newArrayList();
        rates.add(limousineRatePersistenceDto);

        return rates;
    }
}
