package ca.ulaval.glo4003.ws.domain.user;

import ca.ulaval.glo4003.ws.domain.user.exception.InvalidCredentialsException;

public class UserAuthenticationService {

    private UserRepository userRepository;

    public UserAuthenticationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void authenticate(User userToAuthenticate) {
        User validUser = userRepository.findByName(userToAuthenticate.getUserName());
        if (validUser == null || !validUser.isTheSameAs(userToAuthenticate)) {
            throw new InvalidCredentialsException("Credentials are invalid.");
        }
    }
}
