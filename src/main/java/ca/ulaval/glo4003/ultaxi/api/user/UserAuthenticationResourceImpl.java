package ca.ulaval.glo4003.ultaxi.api.user;

import ca.ulaval.glo4003.ultaxi.service.user.UserAuthenticationService;
import ca.ulaval.glo4003.ultaxi.transfer.user.UserDto;

import javax.ws.rs.core.Response;

public class UserAuthenticationResourceImpl implements UserAuthenticationResource {

    private UserAuthenticationService userAuthenticationService;

    public UserAuthenticationResourceImpl(UserAuthenticationService userAuthenticationService) {
        this.userAuthenticationService = userAuthenticationService;
    }

    @Override
    public Response authenticateUser(UserDto userDto) {
        String token = userAuthenticationService.authenticate(userDto);
        return Response.ok().entity(token).build();
    }

    @Override
    public Response signOut(String userToken) {
        userAuthenticationService.deauthenticate(userToken);
        return Response.status(Response.Status.RESET_CONTENT).build();
    }

}
