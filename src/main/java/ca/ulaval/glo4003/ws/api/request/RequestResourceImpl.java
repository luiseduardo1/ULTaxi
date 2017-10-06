package ca.ulaval.glo4003.ws.api.request;

import ca.ulaval.glo4003.ws.api.request.dto.RequestDto;
import ca.ulaval.glo4003.ws.domain.geolocation.InvalidGeolocationException;
import ca.ulaval.glo4003.ws.domain.request.RequestService;
import ca.ulaval.glo4003.ws.domain.vehicle.InvalidVehicleTypeException;

import javax.ws.rs.core.Response;

public class RequestResourceImpl implements RequestResource {

    private RequestService requestService;

    public RequestResourceImpl(RequestService requestService) {
        this.requestService = requestService;
    }

    @Override
    public Response sendTransportRequest(RequestDto requestDto) {
        try {
            String requestId = requestService.sendTransportRequest(requestDto);
            return Response.status(Response.Status.CREATED).entity(requestId).build();
        } catch (InvalidVehicleTypeException | InvalidGeolocationException exception) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}
