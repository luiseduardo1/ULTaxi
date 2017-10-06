package ca.ulaval.glo4003.ultaxi.transfer.transportrequest;

import ca.ulaval.glo4003.ultaxi.domain.geolocation.Geolocation;
import ca.ulaval.glo4003.ultaxi.domain.transportrequest.TransportRequest;

public class TransportRequestAssembler {

    public TransportRequest create(TransportRequestDto transportRequestDto) {
        Geolocation geolocation = new Geolocation();
        geolocation.setLatitude(transportRequestDto.getLatitude());
        geolocation.setLongitude(transportRequestDto.getLongitude());
        TransportRequest transportRequest = new TransportRequest();
        transportRequest.setGeolocation(geolocation);
        transportRequest.setNote(transportRequestDto.getNote());
        transportRequest.setVehicleType(transportRequestDto.getVehicleType());
        return transportRequest;
    }
}
