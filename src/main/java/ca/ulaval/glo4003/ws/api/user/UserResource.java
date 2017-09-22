package ca.ulaval.glo4003.ws.api.user;

import ca.ulaval.glo4003.ws.api.user.dto.UserDto;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/users")
public interface UserResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    List<UserDto> getUsers();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    UserDto getUser(
        @PathParam("id") String id);

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    void createUser(UserDto userDto);

    @PUT
    @Path("{id}")
    void updateUser(@PathParam("id") String id, UserDto userDto);

    @DELETE
    @Path("{id}")
    void deleteUser(@PathParam("id") String id);
}
