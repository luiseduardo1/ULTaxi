package ca.ulaval.glo4003.ws.domain.user;

public class UserAuthenticationService {

    private UserRepository userRepository;

    public UserAuthenticationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
