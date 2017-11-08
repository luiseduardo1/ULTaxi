package ca.ulaval.glo4003.ultaxi.api.transportrequest;

import ca.ulaval.glo4003.ultaxi.domain.user.Role;
import ca.ulaval.glo4003.ultaxi.http.authentication.filtering.Secured;
import ca.ulaval.glo4003.ultaxi.transfer.transportrequest.TransportRequestDto;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/transport-requests")
public interface TransportRequestResource {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured({Role.CLIENT})
    Response sendTransportRequest(@HeaderParam(value = "Authorization") String clientToken, TransportRequestDto
        transportRequestDto);

    @GET
    @Path("/search")
    @Produces(MediaType.APPLICATION_JSON)
    @Secured({Role.DRIVER})
    Response searchAvailableTransportRequests(@HeaderParam(value = "Authorization") String driverToken);

    @POST
    @Path("/assign")
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured({Role.DRIVER})
    Response assignTransportRequest(@HeaderParam(value = "Authorization") String clientToken,
                                    String transportRequestId);
}
