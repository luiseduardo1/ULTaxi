package ca.ulaval.glo4003.ws.domain.user;

public class UserAuthenticationService {

    private UserRepository userRepository;

    public UserAuthenticationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void authenticate(User userToAuthenticate) {
        User validUser = userRepository.findByName(userToAuthenticate.getName());
        if(!validUser.isTheSameAs(userToAuthenticate)) {
            throw new InvalidCredentialsException("Credential are invalid");
        }
    }

}
