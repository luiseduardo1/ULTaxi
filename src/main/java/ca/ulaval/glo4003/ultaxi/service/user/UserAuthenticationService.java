package ca.ulaval.glo4003.ultaxi.service.user;

import ca.ulaval.glo4003.ultaxi.domain.user.TokenManager;
import ca.ulaval.glo4003.ultaxi.domain.user.TokenRepository;
import ca.ulaval.glo4003.ultaxi.domain.user.User;
import ca.ulaval.glo4003.ultaxi.domain.user.UserRepository;
import ca.ulaval.glo4003.ultaxi.domain.user.exception.InvalidCredentialsException;
import ca.ulaval.glo4003.ultaxi.transfer.user.UserAssembler;
import ca.ulaval.glo4003.ultaxi.transfer.user.UserDto;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class UserAuthenticationService {

    private static final List<String> AUTHENTICATION_SCHEMES = Collections.unmodifiableList(Arrays.asList("Bearer",
            "Basic",
            "Digest"));
    private static long HOUR_IN_MILLISECONDS = 3600000;
    private UserRepository userRepository;
    private UserAssembler userAssembler;
    private TokenManager tokenManager;
    private TokenRepository tokenRepository;

    public UserAuthenticationService(UserRepository userRepository, UserAssembler userAssembler, TokenManager tokenManager, TokenRepository tokenRepository) {
        this.userRepository = userRepository;
        this.userAssembler = userAssembler;
        this.tokenManager = tokenManager;
        this.tokenRepository = tokenRepository;
    }

    public String authenticate(UserDto userDto) {
        User user = userAssembler.create(userDto);
        User validUser = userRepository.findByUsername(user.getUsername());
        if (validUser == null || !validUser.areCredentialsValid(user.getUsername(), userDto.getPassword())) {
            throw new InvalidCredentialsException("Credentials are invalid.");
        }
        return generateToken(validUser.getUsername());
    }

    public void deauthenticate(String token) {
        tokenRepository.delete(tokenManager.getTokenId(extractToken(token)));
    }

    public User authenticateFromToken(String token) {
        String username = tokenManager.getUsername(extractToken(token));
        return userRepository.findByUsername(username);
    }

    private String generateToken(String username) {
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
