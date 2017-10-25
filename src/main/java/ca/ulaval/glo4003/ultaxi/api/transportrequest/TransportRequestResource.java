package ca.ulaval.glo4003.ultaxi.api.transportrequest;

import ca.ulaval.glo4003.ultaxi.domain.user.Role;
import ca.ulaval.glo4003.ultaxi.http.authentication.filtering.Secured;
import ca.ulaval.glo4003.ultaxi.transfer.transportrequest.TransportRequestDto;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/transportRequest")
public interface TransportRequestResource {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured({Role.Client})
    Response sendTransportRequest(@HeaderParam(value = "Authorization") String clientToken, TransportRequestDto transportRequestDto);

    @GET
    @Path("/search")
    @Produces(MediaType.APPLICATION_JSON)
    @Secured({Role.Driver})
    Response searchAvailableTransportRequest(@HeaderParam(value = "Authorization") String driverToken);
}
