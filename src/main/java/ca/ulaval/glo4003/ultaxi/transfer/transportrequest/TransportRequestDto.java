package ca.ulaval.glo4003.ultaxi.transfer.transportrequest;

public class TransportRequestDto {

    private double startingPositionLatitude;
    private double startingPositionLongitude;
    private String vehicleType;
    private String note;
    private String clientUsername;

    public double getStartingPositionLongitude() {
        return startingPositionLongitude;
    }

    public void setStartingPositionLongitude(double startingPositionLongitude) {
        this.startingPositionLongitude = startingPositionLongitude;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getVehicleType() {
        return this.vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public double getStartingPositionLatitude() {
        return startingPositionLatitude;
    }

    public void setStartingPositionLatitude(double startingPositionLatitude) {
        this.startingPositionLatitude = startingPositionLatitude;
    }

    public String getClientUsername() {
        return clientUsername;
    }

    public void setClientUsername(String clientUsername) {
        this.clientUsername = clientUsername;
    }
}
