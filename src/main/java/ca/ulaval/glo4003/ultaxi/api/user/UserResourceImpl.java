package ca.ulaval.glo4003.ultaxi.api.user;

import ca.ulaval.glo4003.ultaxi.domain.user.exception.InvalidUserNameException;
import ca.ulaval.glo4003.ultaxi.domain.user.exception.UserAlreadyExistsException;
import ca.ulaval.glo4003.ultaxi.service.user.UserService;
import ca.ulaval.glo4003.ultaxi.transfer.user.UserDto;

import javax.ws.rs.core.Response;

public class UserResourceImpl implements UserResource {

    private UserService userService;

    public UserResourceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Response createUser(UserDto userDto) {
        try {
            userService.addUser(userDto);
            return Response.ok().build();
        } catch (UserAlreadyExistsException | InvalidUserNameException exception) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}
