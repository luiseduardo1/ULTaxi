package ca.ulaval.glo4003.ultaxi.api.user;

import ca.ulaval.glo4003.ultaxi.domain.user.exception.InvalidCredentialsException;
import ca.ulaval.glo4003.ultaxi.infrastructure.user.jwt.exception.InvalidTokenException;
import ca.ulaval.glo4003.ultaxi.service.user.UserAuthenticationService;
import ca.ulaval.glo4003.ultaxi.transfer.user.AuthenticationDto;

import javax.ws.rs.core.Response;

public class UserAuthenticationResourceImpl implements UserAuthenticationResource {

    private UserAuthenticationService userAuthenticationService;

    public UserAuthenticationResourceImpl(UserAuthenticationService userAuthenticationService) {
        this.userAuthenticationService = userAuthenticationService;
    }

    @Override
    public Response authenticateUser(AuthenticationDto authenticationDto) throws InvalidTokenException,
        InvalidCredentialsException {
        String token = userAuthenticationService.authenticate(authenticationDto);
        return Response.ok().entity(token).build();
    }

    @Override
    public Response signOut(String userToken) throws InvalidTokenException {
        userAuthenticationService.deauthenticate(userToken);
        return Response.status(Response.Status.RESET_CONTENT).build();
    }

}
