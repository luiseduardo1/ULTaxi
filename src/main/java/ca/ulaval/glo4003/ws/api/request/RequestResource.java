package ca.ulaval.glo4003.ws.api.request;

import ca.ulaval.glo4003.ws.api.request.dto.RequestDto;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/request")
@RolesAllowed({"CLIENT"})
public interface RequestResource {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    Response sendTransportRequest(RequestDto requestDto);
}
