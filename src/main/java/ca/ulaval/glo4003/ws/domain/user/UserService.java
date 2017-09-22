package ca.ulaval.glo4003.ws.domain.user;

import ca.ulaval.glo4003.ws.api.user.dto.UserDto;

import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class UserService {

    private Logger logger = Logger.getLogger(UserService.class.getName());

    private UserRepository userRepository;
    private UserAssembler userAssembler;

    public UserService(UserRepository userRepository, UserAssembler userAssembler) {
        this.userRepository = userRepository;
        this.userAssembler = userAssembler;
    }

    public List<UserDto> findAllUsers() {
        logger.info("Get all users");
        List<User> users = userRepository.findAll();
        return users.stream().map(userAssembler::create).collect(Collectors.toList());
    }

    public UserDto findUser(String id) {
        logger.info(String.format("Get user with id %s", id));
        User user = userRepository.findById(id);
        return userAssembler.create(user);
    }

    public void addUser(UserDto userDto) {
        logger.info(String.format("Add new user %s", userDto));
        User user = userAssembler.create(userDto);
        user.setId(UUID.randomUUID().toString());
        userRepository.save(user);
    }

    public void updateUser(String id, UserDto userDto) throws UserNotFoundException {
        logger.info(String.format("Update user with id %s", id));
        User user = userAssembler.create(userDto);
        user.setId(id);
        userRepository.update(user);
    }

    public void deleteUser(String id) {
        logger.info(String.format("Delete user with id %s", id));
        userRepository.remove(id);
    }

}
