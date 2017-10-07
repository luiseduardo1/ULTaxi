package ca.ulaval.glo4003.ultaxi.service.user;

import ca.ulaval.glo4003.ultaxi.domain.user.User;
import ca.ulaval.glo4003.ultaxi.domain.user.UserRepository;
import ca.ulaval.glo4003.ultaxi.domain.user.exception.InvalidCredentialsException;

public class UserAuthenticationService {

    private UserRepository userRepository;

    public UserAuthenticationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void authenticate(User userToAuthenticate) {
        User validUser = userRepository.findByUserName(userToAuthenticate.getUserName());
        if (validUser == null || !validUser.isTheSameAs(userToAuthenticate)) {
            throw new InvalidCredentialsException("Credentials are invalid.");
        }
    }
}
