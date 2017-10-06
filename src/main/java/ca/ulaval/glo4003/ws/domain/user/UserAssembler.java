package ca.ulaval.glo4003.ws.domain.user;

import ca.ulaval.glo4003.ws.api.user.dto.UserDto;

public class UserAssembler {

    public User create(UserDto userDto) {
        User user = new User();
        user.setUserName(userDto.getUserName());
        user.setPassword(userDto.getPassword());
        user.setRole(Role.valueOf(userDto.getRole()));
        user.setEmailAddress(userDto.getEmail());
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
