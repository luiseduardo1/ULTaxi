package ca.ulaval.glo4003.ultaxi.transfer.transportrequest;

public class TransportRequestCompleteDto {

    private double endingPositionLatitude;
    private double endingPositionLongitude;
    private String transportRequestId;

    public String getTransportRequestId() {
        return transportRequestId;
    }

    public void setTransportRequestId(String transportRequestId) {
        this.transportRequestId = transportRequestId;
    }

    public double getEndingPositionLongitude() {
        return endingPositionLongitude;
    }

    public void setEndingPPositionLongitude(double startingPositionLongitude) {
        this.endingPositionLongitude = startingPositionLongitude;
    }

    public double getEndingPositionLatitude() {
        return endingPositionLatitude;
    }

    public void setEndingPPositionLatitude(double startingPositionLatitude) {
        this.endingPositionLatitude = startingPositionLatitude;
    }

}
