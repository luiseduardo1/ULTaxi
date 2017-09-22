package ca.ulaval.glo4003.ws.api.user;

import ca.ulaval.glo4003.ws.api.user.dto.UserDto;
import ca.ulaval.glo4003.ws.domain.user.UserNotFoundException;
import ca.ulaval.glo4003.ws.domain.user.UserService;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.util.List;

public class UserResourceImpl implements UserResource {

    private UserService userService;

    public UserResourceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public List<UserDto> getUsers() {
        return userService.findAllUsers();
    }

    @Override
    public UserDto getUser(String id) {
        return userService.findUser(id);
    }

    @Override
    public void createUser(UserDto userDto) {
        userService.addUser(userDto);
    }

    @Override
    public void updateUser(String id, UserDto userDto) {
        try {
            userService.updateUser(id, userDto);
        } catch (UserNotFoundException e) {
            throw new WebApplicationException(
                Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build());
        }
    }

    @Override
    public void deleteUser(String id) {
        userService.deleteUser(id);
    }
}
