package ca.ulaval.glo4003.ultaxi.utils.distancecalculator;

public interface DistanceCalculatorStrategy {

    Double calculDistance(double startLatitude, double startLongitude, double endLatitude, double endLongitude);

}
