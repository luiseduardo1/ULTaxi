package ca.ulaval.glo4003.ultaxi.transfer.rate;

import ca.ulaval.glo4003.ultaxi.domain.rate.DistanceRate;

public class RatePersistenceAssembler {

    public RatePersistenceDto create(DistanceRate distanceRate) {
        RatePersistenceDto ratePersistenceDto = new RatePersistenceDto();
        ratePersistenceDto.setRate(distanceRate.getRate());
        ratePersistenceDto.setVehicleType(distanceRate.getVehicleType().name());
        ratePersistenceDto.setRateType(distanceRate.getRateType());
        return ratePersistenceDto;
    }

    public DistanceRate create(RatePersistenceDto ratePersistenceDto) {
        DistanceRate distanceRate = new DistanceRate();
        distanceRate.setVehicleType(ratePersistenceDto.getVehicleType());
        distanceRate.setRate(ratePersistenceDto.getRate());
        distanceRate.setRateType(distanceRate.getRateType());
        return distanceRate;
    }

}
