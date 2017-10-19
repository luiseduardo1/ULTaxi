package ca.ulaval.glo4003.ultaxi.transfer.user;

import ca.ulaval.glo4003.ultaxi.domain.user.User;
import ca.ulaval.glo4003.ultaxi.utils.hashing.HashingStrategy;

public class UserAssembler {

    private final HashingStrategy hashingStrategy;

    public UserAssembler(HashingStrategy hashingStrategy) {
        this.hashingStrategy = hashingStrategy;
    }

    public User create(UserDto userDto) {
        User user = new User();
        user.setUsername(userDto.getUserName());
        user.setPassword(userDto.getPassword(), hashingStrategy);
        user.setEmailAddress(userDto.getEmail());
        return user;
    }

    public UserDto create(User user) {
        UserDto userDto = new UserDto();
        userDto.setUserName(user.getUsername());
        userDto.setPassword(user.getPassword());
        userDto.setEmail(user.getEmailAddress());
        return userDto;
    }
}
