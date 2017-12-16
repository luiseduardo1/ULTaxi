package ca.ulaval.glo4003.ultaxi.utils.distancecalculator;

public interface CalculateDistanceStrategy {

    Double calculDistance(double startLatitude, double startLongitude, double endLatitude, double endLongitude);

}
