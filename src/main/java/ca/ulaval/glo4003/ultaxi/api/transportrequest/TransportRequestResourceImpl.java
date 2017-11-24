package ca.ulaval.glo4003.ultaxi.api.transportrequest;

import ca.ulaval.glo4003.ultaxi.domain.user.Role;
import ca.ulaval.glo4003.ultaxi.http.authentication.filtering.Secured;
import ca.ulaval.glo4003.ultaxi.service.transportrequest.TransportRequestService;
import ca.ulaval.glo4003.ultaxi.transfer.transportrequest.TransportRequestDto;

import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import java.util.List;

public class TransportRequestResourceImpl implements TransportRequestResource {

    private TransportRequestService transportRequestService;

    public TransportRequestResourceImpl(TransportRequestService transportRequestService) {
        this.transportRequestService = transportRequestService;
    }

    @Override
    @Secured({Role.CLIENT})
    public Response sendTransportRequest(String clientToken, TransportRequestDto transportRequestDto) {
        String transportRequestId = transportRequestService.sendRequest(transportRequestDto, clientToken);
        return Response.status(Response.Status.CREATED).entity(transportRequestId).build();
    }

    @Override
    @Secured({Role.DRIVER})
    public Response searchAvailableTransportRequests(String driverToken) {
        GenericEntity<List<TransportRequestDto>> availableTransportRequests =
            new GenericEntity<List<TransportRequestDto>>(transportRequestService.searchBy(driverToken)) {
            };

        return Response.ok(availableTransportRequests).build();
    }

    @Override
    @Secured({Role.DRIVER})
    public Response assignTransportRequest(String driverToken, String transportRequestId) {
        transportRequestService.assignTransportRequest(driverToken, transportRequestId);
        return Response.ok().build();
    }
}
