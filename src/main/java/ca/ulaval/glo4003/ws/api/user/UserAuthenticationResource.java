package ca.ulaval.glo4003.ws.api.user;

import ca.ulaval.glo4003.ws.api.user.dto.UserDto;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/users/auth")
public interface UserAuthenticationResource {
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/signin")
    Response authenticateUser(UserDto userDto);

    @POST
    @Path("/signout")
    Response signOut(@HeaderParam(value = "Authorization") String token);
}
