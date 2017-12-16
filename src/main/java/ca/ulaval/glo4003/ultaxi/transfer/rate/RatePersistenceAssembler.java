package ca.ulaval.glo4003.ultaxi.transfer.rate;

import ca.ulaval.glo4003.ultaxi.domain.rate.DistanceRate;

public class RatePersistenceAssembler {

    public RatePersistenceDto create(DistanceRate distanceRate) {
        RatePersistenceDto ratePersistenceDto = new RatePersistenceDto();
        ratePersistenceDto.setValue(distanceRate.getValue());
        ratePersistenceDto.setVehicleType(distanceRate.getVehicleType().name());
        ratePersistenceDto.setRateType(distanceRate.getRateType());
        return ratePersistenceDto;
    }

    public DistanceRate create(RatePersistenceDto ratePersistenceDto) {
        DistanceRate distanceRate = new DistanceRate();
        distanceRate.setVehicleType(ratePersistenceDto.getVehicleType());
        distanceRate.setRate(ratePersistenceDto.getValue());
        distanceRate.setRateType(distanceRate.getRateType());
        return distanceRate;
    }

}
