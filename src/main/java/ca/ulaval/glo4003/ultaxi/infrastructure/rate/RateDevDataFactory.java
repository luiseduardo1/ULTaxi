package ca.ulaval.glo4003.ultaxi.infrastructure.rate;

import ca.ulaval.glo4003.ultaxi.domain.rate.DistanceRate;
import ca.ulaval.glo4003.ultaxi.domain.rate.Rate;
import ca.ulaval.glo4003.ultaxi.domain.vehicle.VehicleType;
import jersey.repackaged.com.google.common.collect.Lists;

import java.math.BigDecimal;
import java.util.List;

public class RateDevDataFactory {
    private static final BigDecimal A_VALID_RATE = BigDecimal.TEN;

    public List<Rate> createMockData() {

        DistanceRate distanceRateLimousine = new DistanceRate();
        distanceRateLimousine.setRate(A_VALID_RATE);
        distanceRateLimousine.setVehicleType(VehicleType.LIMOUSINE);

        List<Rate> rates = Lists.newArrayList();
        rates.add(distanceRateLimousine);

        return rates;
    }
}
