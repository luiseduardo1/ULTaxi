package ca.ulaval.glo4003.ws.api.transportrequest;

import ca.ulaval.glo4003.ws.api.transportrequest.dto.TransportRequestDto;
import ca.ulaval.glo4003.ws.domain.geolocation.InvalidGeolocationException;
import ca.ulaval.glo4003.ws.domain.transportrequest.TransportRequestService;
import ca.ulaval.glo4003.ws.domain.vehicle.InvalidVehicleTypeException;

import javax.ws.rs.core.Response;

public class TransportRequestResourceImpl implements TransportRequestResource {

    private TransportRequestService transportRequestService;

    public TransportRequestResourceImpl(TransportRequestService transportRequestService) {
        this.transportRequestService = transportRequestService;
    }

    @Override
    public Response sendTransportRequest(TransportRequestDto transportRequestDto) {
        try {
            String transportRequestId = transportRequestService.sendRequest(transportRequestDto);
            return Response.status(Response.Status.CREATED).entity(transportRequestId).build();
        } catch (InvalidVehicleTypeException | InvalidGeolocationException exception) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}
