package ca.ulaval.glo4003.ws.domain.transportrequest;

import ca.ulaval.glo4003.ws.api.transportrequest.dto.TransportRequestDto;
import ca.ulaval.glo4003.ws.domain.geolocation.Geolocation;

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
