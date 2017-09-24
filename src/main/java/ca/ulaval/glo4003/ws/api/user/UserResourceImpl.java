package ca.ulaval.glo4003.ws.api.user;

import ca.ulaval.glo4003.ws.api.user.dto.UserDto;
import ca.ulaval.glo4003.ws.domain.user.UserService;

public class UserResourceImpl implements UserResource {

    private UserService userService;

    public UserResourceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void createUser(UserDto userDto) {
        userService.addUser(userDto);
    }
}
