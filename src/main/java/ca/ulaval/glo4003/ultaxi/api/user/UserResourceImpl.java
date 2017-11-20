package ca.ulaval.glo4003.ultaxi.api.user;

import ca.ulaval.glo4003.ultaxi.domain.user.Role;
import ca.ulaval.glo4003.ultaxi.http.authentication.filtering.Secured;
import ca.ulaval.glo4003.ultaxi.service.user.UserService;
import ca.ulaval.glo4003.ultaxi.transfer.user.UserDto;

import javax.ws.rs.core.Response;

public class UserResourceImpl implements UserResource {

    private final UserService userService;

    public UserResourceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Response createUser(UserDto userDto) {
        userService.addUser(userDto);
        return Response.ok().build();
    }

    @Override
    @Secured(Role.CLIENT)
    public Response updateClient(String userToken, UserDto userDto) {
        userService.updateClient(userDto, userToken);
        return Response.ok().build();
    }
}
