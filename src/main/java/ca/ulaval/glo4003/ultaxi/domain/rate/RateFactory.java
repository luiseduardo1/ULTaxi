package ca.ulaval.glo4003.ultaxi.domain.rate;

import ca.ulaval.glo4003.ultaxi.domain.transportrequest.TransportRequest;
import ca.ulaval.glo4003.ultaxi.transfer.rate.RatePersistenceAssembler;
import ca.ulaval.glo4003.ultaxi.transfer.rate.RatePersistenceDto;

public class RateFactory {

    public static Rate getRate(TransportRequest transportRequest, RateRepository rateRepository) {
        RatePersistenceDto ratePersistenceDto = rateRepository
                .findDistanceRateByVehicleType(transportRequest.getVehicleType());
        RatePersistenceAssembler ratePersistenceAssembler = new RatePersistenceAssembler();
        Rate rate = ratePersistenceAssembler.create(ratePersistenceDto);
        return rate;
    }
}
