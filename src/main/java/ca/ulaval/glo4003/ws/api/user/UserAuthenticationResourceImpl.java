package ca.ulaval.glo4003.ws.api.user;

import ca.ulaval.glo4003.ws.api.user.dto.UserDto;
import ca.ulaval.glo4003.ws.domain.user.InvalidCredentialsException;
import ca.ulaval.glo4003.ws.domain.user.TokenManager;
import ca.ulaval.glo4003.ws.domain.user.UserService;
import ca.ulaval.glo4003.ws.infrastructure.user.TokenRepository;

import javax.ws.rs.core.Response;

public class UserAuthenticationResourceImpl implements UserAuthenticationResource {

    private UserService userService;

    private TokenManager tokenManager;

    private TokenRepository tokenRepository;

    private static long HOUR_IN_MILLISECONDS = 3600000;

    public UserAuthenticationResourceImpl(UserService userService, TokenRepository tokenRepository,
                                          TokenManager tokenManager) {
        this.userService = userService;
        this.tokenManager = tokenManager;
        this.tokenRepository = tokenRepository;
    }

    @Override
    public Response authenticateUser(UserDto userDto) {
        try {
            if (userService.authenticate(userDto)) {
                String token = tokenManager.createToken(userDto.getName(), HOUR_IN_MILLISECONDS);
                tokenRepository.save(tokenManager.getTokenId(token), token);
                return Response.ok().entity(token).build();
            }
        } catch (InvalidCredentialsException exception) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        return Response.status(Response.Status.FORBIDDEN).build();
    }

    @Override
    public Response signOut(String token) {
        tokenRepository.delete(tokenManager.getTokenId(token));
        return Response.status(Response.Status.RESET_CONTENT).build();
    }
}
