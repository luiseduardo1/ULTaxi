package ca.ulaval.glo4003.ws.api.user;

import ca.ulaval.glo4003.ws.api.user.dto.UserDto;
import ca.ulaval.glo4003.ws.domain.user.InvalidPasswordException;
import ca.ulaval.glo4003.ws.domain.user.InvalidUserNameException;
import ca.ulaval.glo4003.ws.domain.user.UserAlreadyExistsException;
import ca.ulaval.glo4003.ws.domain.user.UserService;

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
        } catch (UserAlreadyExistsException | InvalidUserNameException | InvalidPasswordException exception) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}
