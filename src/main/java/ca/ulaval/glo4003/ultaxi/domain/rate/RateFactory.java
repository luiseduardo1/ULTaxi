package ca.ulaval.glo4003.ultaxi.domain.rate;

import ca.ulaval.glo4003.ultaxi.domain.geolocation.Geolocation;

public class RateFactory {

    public static Rate getRate(Geolocation startingPosition, Geolocation endingPosition) {
        return new DistanceRate();
    }
}
