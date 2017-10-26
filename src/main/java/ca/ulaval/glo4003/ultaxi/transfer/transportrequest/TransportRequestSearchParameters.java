package ca.ulaval.glo4003.ultaxi.transfer.transportrequest;

public class TransportRequestSearchParameters {

    private final String vehicleType;

    public TransportRequestSearchParameters(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getVehicleType() {
        return vehicleType;
    }
}
