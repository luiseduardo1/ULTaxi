package ca.ulaval.glo4003.ws.domain.user;

import ca.ulaval.glo4003.ws.api.user.dto.UserDto;

public class UserAssembler {

    public User create(UserDto userDto) {
        User user = new User();
        user.setAddress(userDto.address);
        user.setTelephoneNumber(userDto.telephoneNumber);
        user.setName(userDto.name);
        return user;
    }

    public UserDto create(User user) {
        UserDto userDto = new UserDto();
        userDto.address = user.getAddress();
        userDto.telephoneNumber = user.getTelephoneNumber();
        userDto.name = user.getName();
        userDto.id = user.getId();
        return userDto;
    }
}
