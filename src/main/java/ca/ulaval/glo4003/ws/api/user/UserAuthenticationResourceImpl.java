package ca.ulaval.glo4003.ws.api.user;

import ca.ulaval.glo4003.ws.api.user.dto.UserDto;
import ca.ulaval.glo4003.ws.domain.user.InvalidCredentialsException;
import ca.ulaval.glo4003.ws.domain.user.UserService;

import javax.ws.rs.core.Response;

public class UserAuthenticationResourceImpl implements UserAuthenticationResource {

    private UserService userService;

    public UserAuthenticationResourceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Response authenticateUser(UserDto userDto) {
        try {
            if(userService.authenticate(userDto)){
                return Response.ok().build();
            }
        } catch (InvalidCredentialsException exception) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        return Response.status(Response.Status.FORBIDDEN).build();
    }
}
