package ca.ulaval.glo4003.ultaxi.utils.distanceCalculator;

public interface DistanceCalculatorStrategy {

    Double getDistance(double startLatitude, double startLongitude, double endLatitude, double endLongitude);

}
