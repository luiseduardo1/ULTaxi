package ca.ulaval.glo4003.ultaxi.transfer.rate;

import ca.ulaval.glo4003.ultaxi.domain.rate.DistanceRate;

public class DistanceRateAssembler {

    public DistanceRate create(DistanceRateDto distanceRateDto) {
        DistanceRate distanceRate = new DistanceRate();
        distanceRate.setRate(distanceRateDto.getRate());
        distanceRate.setVehicleType(distanceRateDto.getVehicleType());
        return distanceRate;
    }

    public DistanceRateDto create(DistanceRate distanceRate) {
        DistanceRateDto distanceRateDto = new DistanceRateDto();
        distanceRateDto.setRate(distanceRate.getValue());
        distanceRateDto.setVehicleType(distanceRate.getVehicleType().name());
        return distanceRateDto;
    }
}
