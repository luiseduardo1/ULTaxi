package ca.ulaval.glo4003.ultaxi.domain.rate;

import ca.ulaval.glo4003.ultaxi.domain.transportrequest.TransportRequest;

public class RateFactory {

    public static Rate getRate(TransportRequest transportRequest, RateRepository rateRepository) {
        return rateRepository.findDistanceRateByVehicleType(transportRequest.getVehicleType());
    }
}
