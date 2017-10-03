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
        request.setId(requestDto.getId());
        request.setNote(requestDto.getNote());
        request.setVehicleType(requestDto.getVehicleType());
        return request;
    }

    public RequestDto create(Request request) {
        RequestDto requestDto = new RequestDto();
        requestDto.setId(request.getId());
        requestDto.setVehicleType(request.getVehicleType());
        requestDto.setNote(request.getNote());
        requestDto.setLatitude(request.getGeolocation().getLatitude());
        requestDto.setLongitude(request.getGeolocation().getLongitude());
        return requestDto;
    }
}
