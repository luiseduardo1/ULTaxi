package ca.ulaval.glo4003.ws.api.request;

import ca.ulaval.glo4003.ws.api.request.dto.RequestDto;
import ca.ulaval.glo4003.ws.domain.request.RequestService;

import javax.ws.rs.core.Response;

public class RequestResourceImpl implements RequestResource {

    private RequestService requestService;

    public RequestResourceImpl(RequestService requestService) {
        this.requestService = requestService;
    }

    @Override
    public Response sendTransportRequest(RequestDto requestDto) {
        try {
            requestService.sendTransportRequest(requestDto);
            return Response.ok().build();
        } catch (InvalidRequestException exception) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}
