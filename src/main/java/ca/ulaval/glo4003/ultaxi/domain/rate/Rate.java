package ca.ulaval.glo4003.ultaxi.domain.rate;

import ca.ulaval.glo4003.ultaxi.domain.geolocation.Geolocation;
import ca.ulaval.glo4003.ultaxi.domain.money.Money;
import ca.ulaval.glo4003.ultaxi.domain.rate.exception.InvalidRateException;
import ca.ulaval.glo4003.ultaxi.utils.distancecalculator.CalculateDistanceStrategy;
import ca.ulaval.glo4003.ultaxi.utils.distancecalculator.HaversineDistance;

import java.math.BigDecimal;

public abstract class Rate {

    protected BigDecimal rate;
    private RateType rateType;

    private CalculateDistanceStrategy distanceCalculatorStrategy;

    public Rate(RateType rateType) {
        this.rateType = rateType;
    }

    public BigDecimal getValue() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        if (!(rate.compareTo(BigDecimal.ZERO) > 0)) {
            throw new InvalidRateException("Rate must be greater than zero");
        }
        this.rate = rate;
    }

    public RateType getRateType() {
        return rateType;
    }

    public Money calculateTotalAmount(Geolocation startPosition, Geolocation endPosition) {
        Double distance = calculateDistance(startPosition, endPosition);

        return new Money(BigDecimal.valueOf(distance).multiply(this.rate));
    }

    private Double calculateDistance(Geolocation startingPosition, Geolocation endingPosition) {
        distanceCalculatorStrategy = new HaversineDistance();
        return distanceCalculatorStrategy.calculDistance(startingPosition.getLatitude(), startingPosition
                .getLongitude(), endingPosition.getLatitude(), endingPosition.getLongitude());
    }

}
