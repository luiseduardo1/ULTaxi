package ca.ulaval.glo4003.ultaxi.api.transportrequest;

import ca.ulaval.glo4003.ultaxi.api.middleware.authentication.Secured;
import ca.ulaval.glo4003.ultaxi.domain.geolocation.exception.InvalidGeolocationException;
import ca.ulaval.glo4003.ultaxi.domain.user.Role;
import ca.ulaval.glo4003.ultaxi.domain.user.exception.InvalidUserRoleException;
import ca.ulaval.glo4003.ultaxi.domain.vehicle.exception.InvalidVehicleTypeException;
import ca.ulaval.glo4003.ultaxi.service.transportrequest.TransportRequestService;
import ca.ulaval.glo4003.ultaxi.transfer.transportrequest.TransportRequestDto;

import javax.ws.rs.core.Response;

@Secured({Role.Client})
public class TransportRequestResourceImpl implements TransportRequestResource {

    private final TransportRequestService transportRequestService;

    public TransportRequestResourceImpl(TransportRequestService transportRequestService) {
        this.transportRequestService = transportRequestService;
    }

    @Override
    public Response sendTransportRequest(TransportRequestDto transportRequestDto) {
        try {
            String transportRequestId = transportRequestService.sendRequest(transportRequestDto);
            return Response.status(Response.Status.CREATED).entity(transportRequestId).build();
        } catch (InvalidVehicleTypeException | InvalidGeolocationException | InvalidUserRoleException exception) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}
