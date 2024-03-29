package ca.ulaval.glo4003.ultaxi.api.user;

import ca.ulaval.glo4003.ultaxi.transfer.user.AuthenticationDto;

import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/users/auth")
public interface UserAuthenticationResource {

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/signin")
    Response authenticateUser(AuthenticationDto authenticationDto);

    @POST
    @Path("/signout")
    Response signOut(@HeaderParam(value = "Authorization") String userToken);
}
