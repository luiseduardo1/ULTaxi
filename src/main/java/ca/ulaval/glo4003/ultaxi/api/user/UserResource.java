package ca.ulaval.glo4003.ultaxi.api.user;

import ca.ulaval.glo4003.ultaxi.domain.user.Role;
import ca.ulaval.glo4003.ultaxi.http.authentication.filtering.Secured;
import ca.ulaval.glo4003.ultaxi.transfer.user.UserDto;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/users")
public interface UserResource {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    Response createUser(UserDto userDto);

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured(Role.CLIENT)
    Response updateUser(@HeaderParam(value = "Authorization") String userToken, UserDto userDto);
}
