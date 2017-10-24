package ca.ulaval.glo4003.ultaxi.domain.geolocation;

import ca.ulaval.glo4003.ultaxi.domain.geolocation.exception.InvalidGeolocationException;

public class Geolocation {

    private static final double LATITUDE_MIN = -90.0;
    private static final double LATITUDE_MAX = 90.0;
    private static final double LONGITUDE_MIN = -180.0;
    private static final double LONGITUDE_MAX = 180.0;
    private double latitude;
    private double longitude;

    public Geolocation(){
    }

    public Geolocation(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
        if (!isLatitudeValid()) {
            throw new InvalidGeolocationException("The latitude of the geolocation is not valid");
        }
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
        if (!isLongitudeValid()) {
            throw new InvalidGeolocationException("The longitude of the geolocation is not valid");
        }
    }

    public boolean isLatitudeValid() {
        return latitude >= LATITUDE_MIN && latitude <= LATITUDE_MAX;
    }

    public boolean isLongitudeValid() {
        return longitude >= LONGITUDE_MIN && longitude <= LONGITUDE_MAX;
    }
}
