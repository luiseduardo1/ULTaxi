package ca.ulaval.glo4003.ultaxi.service.rate;

import ca.ulaval.glo4003.ultaxi.domain.rate.DistanceRate;
import ca.ulaval.glo4003.ultaxi.domain.rate.RateRepository;
import ca.ulaval.glo4003.ultaxi.transfer.rate.DistanceRateAssembler;
import ca.ulaval.glo4003.ultaxi.transfer.rate.DistanceRateDto;

import java.util.logging.Logger;

public class RateService {

    private final Logger logger = Logger.getLogger(RateService.class.getName());
    private final DistanceRateAssembler distanceRateAssembler;
    private RateRepository rateRepository;

    public RateService(RateRepository rateRepository, DistanceRateAssembler distanceRateAssembler) {
        this.rateRepository = rateRepository;
        this.distanceRateAssembler = distanceRateAssembler;
    }

    public void addDistanceRate(DistanceRateDto distanceRateDto) {
        logger.info(String.format("Add new distance rate %s", distanceRateDto));
        DistanceRate distanceRate = distanceRateAssembler.create(distanceRateDto);
        rateRepository.save(distanceRate);
    }

    public void updateDistanceRate(DistanceRateDto distanceRateDto) {
        logger.info(String.format("Update distance rate with infos %s", distanceRateDto));
        DistanceRate distanceRate = distanceRateAssembler.create(distanceRateDto);
        rateRepository.update(distanceRate);
    }
}
