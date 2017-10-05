package ca.ulaval.glo4003.ws.domain.user;

import ca.ulaval.glo4003.ws.api.user.dto.UserDto;

import java.util.logging.Logger;

public class UserService {

    private Logger logger = Logger.getLogger(UserService.class.getName());

    private UserRepository userRepository;
    private UserAssembler userAssembler;
    private UserAuthenticationService userAuthenticationService;

    public UserService(UserRepository userRepository, UserAssembler userAssembler,
                       UserAuthenticationService userAuthenticationService) {
        this.userRepository = userRepository;
        this.userAssembler = userAssembler;
        this.userAuthenticationService = userAuthenticationService;
    }

    public void addUser(UserDto userDto) {
        logger.info(String.format("Add new user %s.", userDto));
        User user = userAssembler.create(userDto);
        userRepository.save(user);
    }

    public void authenticate(UserDto userDto) {
        logger.info(String.format("Authenticating user %s.", userDto));
        User user = userAssembler.create(userDto);
        userAuthenticationService.authenticate(user);
    }
}
