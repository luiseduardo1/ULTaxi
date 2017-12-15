package ca.ulaval.glo4003.ultaxi.service.user;

import ca.ulaval.glo4003.ultaxi.domain.user.TokenManager;
import ca.ulaval.glo4003.ultaxi.domain.user.TokenRepository;
import ca.ulaval.glo4003.ultaxi.domain.user.User;
import ca.ulaval.glo4003.ultaxi.domain.user.UserRepository;
import ca.ulaval.glo4003.ultaxi.domain.user.exception.InvalidCredentialsException;
import ca.ulaval.glo4003.ultaxi.infrastructure.user.jwt.exception.InvalidTokenException;
import ca.ulaval.glo4003.ultaxi.transfer.user.AuthenticationDto;
import ca.ulaval.glo4003.ultaxi.transfer.user.client.ClientAssembler;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class UserAuthenticationService {

    private static final List<String> AUTHENTICATION_SCHEMES = Collections.unmodifiableList(
        Arrays.asList("Bearer", "Basic", "Digest")
    );
    private static final long HOUR_IN_MILLISECONDS = 3600000;
    private final TokenManager tokenManager;
    private final TokenRepository tokenRepository;
    private final UserRepository userRepository;
    private final ClientAssembler clientAssembler;

    public UserAuthenticationService(UserRepository userRepository, ClientAssembler clientAssembler, TokenManager
        tokenManager, TokenRepository tokenRepository) {
        this.userRepository = userRepository;
        this.clientAssembler = clientAssembler;
        this.tokenManager = tokenManager;
        this.tokenRepository = tokenRepository;
    }

    public String authenticate(AuthenticationDto authenticationDto) throws InvalidTokenException {
        User user = userRepository.findByUsername(authenticationDto.getUsername());
        if (user == null ||
            !user.areValidCredentials(authenticationDto.getUsername(), authenticationDto.getPassword())) {
            throw new InvalidCredentialsException("Credentials are invalid.");
        }
        return generateToken(user.getUsername());
    }

    public void deauthenticate(String token) throws InvalidTokenException {
        tokenRepository.delete(tokenManager.getTokenId(extractToken(token)));
    }

    public User getUserFromToken(String userToken) throws InvalidTokenException {
        String username = tokenManager.getUsername(extractToken(userToken));
        return userRepository.findByUsername(username);
    }

    private String generateToken(String username) throws InvalidTokenException {
        String token = tokenManager.createToken(username, HOUR_IN_MILLISECONDS);
        tokenRepository.save(tokenManager.getTokenId(token), token);
        return token;
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
