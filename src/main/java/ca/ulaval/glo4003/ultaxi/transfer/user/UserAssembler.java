package ca.ulaval.glo4003.ultaxi.transfer.user;

import ca.ulaval.glo4003.ultaxi.utils.hashing.HashingStrategy;
import ca.ulaval.glo4003.ultaxi.domain.user.Role;
import ca.ulaval.glo4003.ultaxi.domain.user.User;

public class UserAssembler {

    private final HashingStrategy hashingStrategy;

    public UserAssembler(HashingStrategy hashingStrategy) {
        this.hashingStrategy = hashingStrategy;
    }

    public User create(UserDto userDto) {
        User user = new User();
        user.setUserName(userDto.getUserName());
        user.setPassword(userDto.getPassword());
        user.setRole(Role.valueOf(userDto.getRole()));
        user.setEmailAddress(userDto.getEmail());
        user.setHashingStrategy(hashingStrategy);
        return user;
    }

    public UserDto create(User user) {
        UserDto userDto = new UserDto();
        userDto.setUserName(user.getUserName());
        userDto.setPassword(user.getPassword());
        userDto.setRole(user.getRole().name());
        userDto.setEmail(user.getEmailAddress());
        return userDto;
    }
}
