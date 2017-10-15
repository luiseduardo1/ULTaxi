package ca.ulaval.glo4003.ultaxi.api.user;

import ca.ulaval.glo4003.ultaxi.domain.user.TokenManager;
import ca.ulaval.glo4003.ultaxi.domain.user.TokenRepository;
import ca.ulaval.glo4003.ultaxi.domain.user.exception.InvalidCredentialsException;
import ca.ulaval.glo4003.ultaxi.service.user.UserAuthenticationService;
import ca.ulaval.glo4003.ultaxi.transfer.user.UserDto;

import javax.ws.rs.core.Response;

public class UserAuthenticationResourceImpl implements UserAuthenticationResource {

    private static long HOUR_IN_MILLISECONDS = 3600000;
    private UserAuthenticationService userAuthenticationService;
    private TokenManager tokenManager;
    private TokenRepository tokenRepository;

    public UserAuthenticationResourceImpl(UserAuthenticationService userAuthenticationService, TokenRepository
        tokenRepository,
        TokenManager tokenManager) {
        this.userAuthenticationService = userAuthenticationService;
        this.tokenManager = tokenManager;
        this.tokenRepository = tokenRepository;
    }

    @Override
    public Response authenticateUser(UserDto userDto) {
        try {
            userAuthenticationService.authenticate(userDto);
            String token = tokenManager.createToken(userDto.getUserName(), HOUR_IN_MILLISECONDS);
            tokenRepository.save(tokenManager.getTokenId(token), token);
            return Response.ok().entity(token).build();
        } catch (InvalidCredentialsException exception) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
    }

    @Override
    public Response signOut(String token) {
        tokenRepository.delete(tokenManager.getTokenId(token));
        return Response.status(Response.Status.RESET_CONTENT).build();
    }
}
