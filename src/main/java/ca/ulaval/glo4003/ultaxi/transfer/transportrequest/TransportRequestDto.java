package ca.ulaval.glo4003.ultaxi.transfer.transportrequest;

public class TransportRequestDto {

    private String id;
    private String clientUsername;
    private String vehicleType;
    private String note;
    private double startingPositionLatitude;
    private double startingPositionLongitude;


    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getClientUsername() {
        return clientUsername;
    }

    public void setClientUsername(String clientUsername) {
        this.clientUsername = clientUsername;
    }

    public String getVehicleType() {
        return this.vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
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
