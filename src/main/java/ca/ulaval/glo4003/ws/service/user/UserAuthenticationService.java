package ca.ulaval.glo4003.ws.service.user;

import ca.ulaval.glo4003.ws.domain.user.User;
import ca.ulaval.glo4003.ws.domain.user.UserRepository;
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
