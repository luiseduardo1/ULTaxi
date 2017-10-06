package ca.ulaval.glo4003.ws.transfer.transportrequest;

import ca.ulaval.glo4003.ws.domain.geolocation.Geolocation;
import ca.ulaval.glo4003.ws.domain.transportrequest.TransportRequest;

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
