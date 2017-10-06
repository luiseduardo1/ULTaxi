package ca.ulaval.glo4003.ws.api.transportrequest;

import ca.ulaval.glo4003.ws.domain.geolocation.exception.InvalidGeolocationException;
import ca.ulaval.glo4003.ws.domain.vehicle.exception.InvalidVehicleTypeException;
import ca.ulaval.glo4003.ws.service.transportrequest.TransportRequestService;
import ca.ulaval.glo4003.ws.transfer.transportrequest.TransportRequestDto;

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
