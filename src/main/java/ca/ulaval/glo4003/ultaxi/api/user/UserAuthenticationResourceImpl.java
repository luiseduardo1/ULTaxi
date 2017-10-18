package ca.ulaval.glo4003.ultaxi.api.user;

import ca.ulaval.glo4003.ultaxi.domain.user.exception.InvalidCredentialsException;
import ca.ulaval.glo4003.ultaxi.infrastructure.user.jwt.exception.InvalidTokenException;
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
        try {
            String token = userAuthenticationService.authenticate(userDto);
            return Response.ok().entity(token).build();
        } catch (InvalidCredentialsException exception) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
    }

    @Override
    public Response signOut(String token) {
        try {
            userAuthenticationService.deauthenticate(token);
            return Response.status(Response.Status.RESET_CONTENT).build();
        } catch (InvalidTokenException exception) {
            return Response.status(Response.Status.BAD_REQUEST).entity(exception.getMessage()).build();
        }
    }

}
