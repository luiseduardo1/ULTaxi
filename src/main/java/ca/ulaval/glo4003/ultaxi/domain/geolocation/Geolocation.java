package ca.ulaval.glo4003.ultaxi.domain.geolocation;

import ca.ulaval.glo4003.ultaxi.domain.geolocation.exception.InvalidGeolocationException;

import java.util.function.Predicate;

public class Geolocation {

    private static final double LATITUDE_MIN = -90.0;
    private static final double LATITUDE_MAX = 90.0;
    private static final double LONGITUDE_MIN = -180.0;
    private static final double LONGITUDE_MAX = 180.0;
    private double latitude;
    private double longitude;

    public Geolocation(double latitude, double longitude) {
        this.latitude = validateLatitude(latitude);
        this.longitude = validateLongitude(longitude);
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    private double validateLongitude(double longitude) {
        return validateCoordinate(longitude, longitudeToValidate -> longitudeToValidate < LONGITUDE_MIN || longitudeToValidate > LONGITUDE_MAX);
    }

    private double validateLatitude(double latitude) {
        return validateCoordinate(latitude, latitudeToValidate -> latitudeToValidate < LATITUDE_MIN || latitudeToValidate > LATITUDE_MAX);
    }

    private double validateCoordinate(double coordinate, Predicate<Double> coordinatePredicate) {
        if (coordinatePredicate.test(coordinate)) {
            throw new InvalidGeolocationException("Invalid geolocation coordinate.");
        }
        return coordinate;
    }


}
