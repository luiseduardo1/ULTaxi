package ca.ulaval.glo4003.ultaxi.transfer.transportrequest;

public class TransportRequestDto {

    private String vehicleType;
    private String note;
    private double startingPositionLatitude;
    private double startingPositionlongitude;

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

    public double getStartingPositionlongitude() {
        return startingPositionlongitude;
    }

    public void setStartingPositionlongitude(double startingPositionlongitude) {
        this.startingPositionlongitude = startingPositionlongitude;
    }
}
