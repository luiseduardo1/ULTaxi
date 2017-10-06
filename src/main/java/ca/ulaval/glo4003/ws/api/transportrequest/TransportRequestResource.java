package ca.ulaval.glo4003.ws.api.transportrequest;

import ca.ulaval.glo4003.ws.api.transportrequest.dto.TransportRequestDto;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/transportRequest")
@RolesAllowed( {"CLIENT"})
public interface TransportRequestResource {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    Response sendTransportRequest(TransportRequestDto transportRequestDto);
}
