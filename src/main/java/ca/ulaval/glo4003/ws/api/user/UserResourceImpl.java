package ca.ulaval.glo4003.ws.api.user;

import ca.ulaval.glo4003.ws.api.user.dto.UserDto;
import ca.ulaval.glo4003.ws.domain.user.InvalidUserNameException;
import ca.ulaval.glo4003.ws.domain.user.Role;
import ca.ulaval.glo4003.ws.domain.user.UserAlreadyExistsException;
import ca.ulaval.glo4003.ws.domain.user.UserService;
import ca.ulaval.glo4003.ws.util.Secured;

import javax.ws.rs.core.Response;

public class UserResourceImpl implements UserResource {

    private UserService userService;

    public UserResourceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Response createUser(UserDto userDto) {
        try {
            //a Modifier
            userDto.setRole("Client");
            userService.addUser(userDto);
            return Response.ok().build();
        } catch (UserAlreadyExistsException | InvalidUserNameException exception) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @Override
    @Secured( {Role.Client})
    public Response mySecuredRoute() {
        return Response.status(200).build();
    }
}
