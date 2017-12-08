package ca.ulaval.glo4003.ultaxi.transfer.transportrequest;

public class TransportRequestCompleteDto {

    private double startingPositionLatitude;
    private double startingPositionLongitude;
    private String transportRequestId;

    public String getTransportRequestId() {
        return transportRequestId;
    }

    public void setTransportRequestId(String transportRequestId) {
        this.transportRequestId = transportRequestId;
    }

    public double getStartingPositionLongitude() {
        return startingPositionLongitude;
    }

    public void setStartingPositionLongitude(double startingPositionLongitude) {
        this.startingPositionLongitude = startingPositionLongitude;
    }

    public double getStartingPositionLatitude() {
        return startingPositionLatitude;
    }

    public void setStartingPositionLatitude(double startingPositionLatitude) {
        this.startingPositionLatitude = startingPositionLatitude;
    }

}
