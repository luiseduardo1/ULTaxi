package ca.ulaval.glo4003.ws.domain.request;

import ca.ulaval.glo4003.ws.api.request.dto.RequestDto;
import ca.ulaval.glo4003.ws.domain.geolocation.Geolocation;

public class RequestAssembler {

    public Request create(RequestDto requestDto) {
        Geolocation geolocation = new Geolocation();
        geolocation.setLatitude(requestDto.getLatitude());
        geolocation.setLongitude(requestDto.getLongitude());
        Request request = new Request();
        request.setGeolocation(geolocation);
        request.setNote(requestDto.getNote());
        request.setVehicleType(requestDto.getVehicleType());
        return request;
    }
}
