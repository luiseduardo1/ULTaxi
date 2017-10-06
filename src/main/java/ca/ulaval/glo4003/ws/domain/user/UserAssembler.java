package ca.ulaval.glo4003.ws.domain.user;

import ca.ulaval.glo4003.ws.api.user.dto.UserDto;

public class UserAssembler {

    private final HashingStrategy hashingStrategy;

    public UserAssembler(HashingStrategy hashingStrategy) {
        this.hashingStrategy = hashingStrategy;
    }

    public User create(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getName());
        user.setPassword(userDto.getPassword());
        user.setRole(Role.valueOf(userDto.getRole()));
        user.setEmailAddress(userDto.getEmail());
        user.setHashingStrategy(hashingStrategy);
        return user;
    }

    public UserDto create(User user) {
        UserDto userDto = new UserDto();
        userDto.setName(user.getName());
        userDto.setPassword(user.getPassword());
        userDto.setRole(user.getRole().name());
        userDto.setEmail(user.getEmailAddress());
        return userDto;
    }
}
