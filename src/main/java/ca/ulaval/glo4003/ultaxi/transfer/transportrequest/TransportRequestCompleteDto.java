package ca.ulaval.glo4003.ultaxi.transfer.transportrequest;

public class TransportRequestCompleteDto {

    private double endingPositionLatitude;
    private double endingPositionLongitude;

    public double getEndingPositionLongitude() {
        return endingPositionLongitude;
    }

    public void setEndingPositionLongitude(double startingPositionLongitude) {
        this.endingPositionLongitude = startingPositionLongitude;
    }

    public double getEndingPositionLatitude() {
        return endingPositionLatitude;
    }

    public void setEndingPositionLatitude(double startingPositionLatitude) {
        this.endingPositionLatitude = startingPositionLatitude;
    }

}
