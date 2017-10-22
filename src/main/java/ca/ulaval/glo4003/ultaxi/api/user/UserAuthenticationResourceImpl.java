package ca.ulaval.glo4003.ultaxi.api.user;

import ca.ulaval.glo4003.ultaxi.domain.user.TokenManager;
import ca.ulaval.glo4003.ultaxi.domain.user.TokenRepository;
import ca.ulaval.glo4003.ultaxi.domain.user.exception.InvalidCredentialsException;
import ca.ulaval.glo4003.ultaxi.infrastructure.user.jwt.exception.InvalidTokenException;
import ca.ulaval.glo4003.ultaxi.service.user.UserAuthenticationService;
import ca.ulaval.glo4003.ultaxi.transfer.user.UserDto;

import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class UserAuthenticationResourceImpl implements UserAuthenticationResource {

    private static final List<String> AUTHENTICATION_SCHEMES = Collections.unmodifiableList(Arrays.asList("Bearer",
                                                                                                          "Basic",
                                                                                                          "Digest"));
    private static final long HOUR_IN_MILLISECONDS = 3600000;
    private final UserAuthenticationService userAuthenticationService;
    private final TokenManager tokenManager;
    private final TokenRepository tokenRepository;

    public UserAuthenticationResourceImpl(UserAuthenticationService userAuthenticationService, TokenRepository
            tokenRepository, TokenManager tokenManager) {
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
        try {
            tokenRepository.delete(tokenManager.getTokenId(extractToken(token)));
            return Response.status(Response.Status.RESET_CONTENT).build();
        } catch (InvalidTokenException exception) {
            return Response.status(Response.Status.BAD_REQUEST).entity(exception.getMessage()).build();
        }
    }

    private String extractToken(String unparsedToken) {
        String token = unparsedToken;
        for (String authenticationScheme : AUTHENTICATION_SCHEMES) {
            if (unparsedToken != null && unparsedToken.toLowerCase().contains(authenticationScheme.toLowerCase())) {
                token = unparsedToken.substring(authenticationScheme.length()).trim();
            }
        }

        return token;
    }
}
