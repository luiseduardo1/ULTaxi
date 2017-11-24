package ca.ulaval.glo4003.ultaxi.service.rate;

import ca.ulaval.glo4003.ultaxi.transfer.rate.DistanceRateAssembler;
import ca.ulaval.glo4003.ultaxi.transfer.rate.DistanceRateDto;

import java.util.logging.Logger;

public class RateService {

    private final Logger logger = Logger.getLogger(RateService.class.getName());

    private final DistanceRateAssembler distanceRateAssembler;

    public RateService(DistanceRateAssembler distanceRateAssembler) {
        this.distanceRateAssembler = distanceRateAssembler;
    }

    public void updateDistanceRate(DistanceRateDto distanceRateDto) {

    }
}
