package ca.ulaval.glo4003.ultaxi.domain.geolocation;

import ca.ulaval.glo4003.ultaxi.domain.geolocation.exception.InvalidGeolocationException;

public class Geolocation {

    private static final double LATITUDE_MIN = -90.0;
    private static final double LATITUDE_MAX = 90.0;
    private static final double LONGITUDE_MIN = -180.0;
    private static final double LONGITUDE_MAX = 180.0;
    private final double latitude;
    private final double longitude;

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

    private double validateLatitude(double latitude) {
        if (latitude < LATITUDE_MIN || latitude > LATITUDE_MAX) {
            throw new InvalidGeolocationException("The latitude of the geolocation is not valid");
        }
        return latitude;
    }

    private double validateLongitude(double longitude) {
        if (longitude < LONGITUDE_MIN || longitude > LONGITUDE_MAX) {
            throw new InvalidGeolocationException("The longitude of the geolocation is not valid");
        }
        return longitude;
    }

}
