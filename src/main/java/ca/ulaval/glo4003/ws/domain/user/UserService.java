package ca.ulaval.glo4003.ws.domain.user;

import ca.ulaval.glo4003.ws.api.user.dto.UserDto;

import java.util.logging.Logger;

public class UserService {

    private Logger logger = Logger.getLogger(UserService.class.getName());

    private UserRepository userRepository;
    private UserAssembler userAssembler;

    public UserService(UserRepository userRepository, UserAssembler userAssembler) {
        this.userRepository = userRepository;
        this.userAssembler = userAssembler;
    }

    public void addUser(UserDto userDto) {
        logger.info(String.format("Add new user %s", userDto));
        User user = userAssembler.create(userDto);
        userRepository.save(user);
    }

    public boolean authenticate(UserDto userDto) {
        logger.info(String.format("Authicating user %s", userDto));
        User user = userAssembler.create(userDto);
        return userRepository.authenticate(user);
    }
}
