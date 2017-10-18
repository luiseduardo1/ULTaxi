package ca.ulaval.glo4003.ultaxi.api.transportrequest;

import ca.ulaval.glo4003.ultaxi.api.middleware.authentication.Secured;
import ca.ulaval.glo4003.ultaxi.domain.user.Role;
import ca.ulaval.glo4003.ultaxi.transfer.transportrequest.TransportRequestDto;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/transportRequest")
public interface TransportRequestResource {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured({Role.Client})
    Response sendTransportRequest(TransportRequestDto transportRequestDto);

    @GET
    @Path("/search")
    @Produces(MediaType.APPLICATION_JSON)
    @Secured({Role.Driver})
    Response searchAvailableTransportRequest(@HeaderParam(value = "Authorization") String token);
}
