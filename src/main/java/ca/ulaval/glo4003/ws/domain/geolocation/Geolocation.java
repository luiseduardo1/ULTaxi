package ca.ulaval.glo4003.ws.domain.geolocation;

public class Geolocation {

    private double latitude;
    private double longitude;

    private static final double LATITUDE_MIN = -90.0;
    private static final double LATITUDE_MAX = 90.0;
    private static final double LONGITUDE_MIN = -180.0;
    private static final double LONGITUDE_MAX = 180.0;

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public boolean isLatitudeValid(double latitude) {
        return latitude >= LATITUDE_MIN && latitude <= LATITUDE_MAX;
    }

    public boolean isLongitudeValid(double longitude) {
        return longitude >= LONGITUDE_MIN && longitude <= LONGITUDE_MAX;
    }
}
