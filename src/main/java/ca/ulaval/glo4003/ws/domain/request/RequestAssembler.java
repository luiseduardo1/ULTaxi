package ca.ulaval.glo4003.ws.domain.request;

import ca.ulaval.glo4003.ws.api.request.dto.RequestDto;

public class RequestAssembler {

    public Request create(RequestDto requestDto) {
        Request request = new Request();
        request.setId(requestDto.getId());
        request.setLocation(requestDto.getLocation());
        request.setNote(requestDto.getNote());
        request.setVehiculeType(requestDto.getVehiculeType());
        return request;
    }

    public RequestDto create(Request request) {
        RequestDto requestDto = new RequestDto();
        requestDto.setId(request.getId());
        requestDto.setLocation(request.getLocation());
        requestDto.setNote(request.getNote());
        requestDto.setVehiculeType(request.getVehiculeType());
        return requestDto;
    }
}
