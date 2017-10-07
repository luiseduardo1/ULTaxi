package ca.ulaval.glo4003.ws.api.transportrequest;

import ca.ulaval.glo4003.ws.api.middleware.authentification.Secured;
import ca.ulaval.glo4003.ws.api.transportrequest.dto.TransportRequestDto;
import ca.ulaval.glo4003.ws.domain.user.Role;

import javax.annotation.security.RolesAllowed;
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
