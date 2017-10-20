package ca.ulaval.glo4003.ultaxi.api.transportrequest;

import ca.ulaval.glo4003.ultaxi.domain.user.Role;
import ca.ulaval.glo4003.ultaxi.http.authentication.filtering.Secured;
import ca.ulaval.glo4003.ultaxi.transfer.transportrequest.TransportRequestDto;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/transportRequest")
@Secured({Role.Client})
public interface TransportRequestResource {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    Response sendTransportRequest(TransportRequestDto transportRequestDto);
}
