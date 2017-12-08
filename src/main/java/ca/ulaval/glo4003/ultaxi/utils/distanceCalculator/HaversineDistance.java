package ca.ulaval.glo4003.ultaxi.utils.distanceCalculator;

public class HaversineDistance implements DistanceCalculatorStrategy {

    private static final int EARTH_RADIUS = 6371;

    //  Taken from https://github.com/jasonwinn/haversine/blob/master/Haversine.java (accessed on Thu dev 9 13:03:22 EDT 2017)
    //  Haversine formula
    @Override
    public Double getDistance(double startLatitude, double startLongitude, double endLatitude, double endLongitude) {

        double dLat = Math.toRadians((endLatitude - startLatitude));
        double dLong = Math.toRadians((endLongitude - startLongitude));

        startLatitude = Math.toRadians(startLatitude);
        endLatitude = Math.toRadians(endLatitude);

        double a = haversin(dLat) + Math.cos(startLatitude) * Math.cos(endLatitude) * haversin(dLong);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS * c;
    }

    private static double haversin(double val) {
        return Math.pow(Math.sin(val / 2), 2);
    }
}
