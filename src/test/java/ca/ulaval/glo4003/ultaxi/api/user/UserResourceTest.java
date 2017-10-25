package ca.ulaval.glo4003.ultaxi.api.user;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.verify;

import ca.ulaval.glo4003.ultaxi.domain.user.exception.InvalidPasswordException;
import ca.ulaval.glo4003.ultaxi.domain.user.exception.InvalidUserNameException;
import ca.ulaval.glo4003.ultaxi.domain.user.exception.UserAlreadyExistsException;
import ca.ulaval.glo4003.ultaxi.service.user.UserService;
import ca.ulaval.glo4003.ultaxi.transfer.user.UserDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ws.rs.core.Response;

@RunWith(MockitoJUnitRunner.class)
public class UserResourceTest {

    @Mock
    private UserService userService;
    @Mock
    private UserDto userDto;

    private UserResource userResource;

    @Before
    public void setUp() {
        userResource = new UserResourceImpl(userService);
    }

    @Test
    public void givenUserWithValidName_whenCreateUser_thenReturnsOk() {
        Response response = userResource.createUser(userDto);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void givenANewCreatedUser_whenRegistering_thenDelegateToUserService() {
        userResource.createUser(userDto);
        verify(userService).addUser(userDto);
    }

    @Test
    public void givenAlreadyExistingUser_whenCreateUser_thenReturnsBadRequest() {
        willThrow(new UserAlreadyExistsException("User already exists."))
            .given(userService)
            .addUser(userDto);

        Response response = userResource.createUser(userDto);

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    @Test
    public void givenUserWithInvalidName_whenCreateUser_thenReturnsBadRequest() {
        willThrow(new InvalidUserNameException("User has an invalid userName."))
            .given(userService)
            .addUser(userDto);

        Response response = userResource.createUser(userDto);

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    @Test
    public void givenUserWithEmptyPassword_whenCreateUser_thenReturnsBadRequest() {
        willThrow(new InvalidPasswordException("User has an invalid password."))
            .given(userService)
            .addUser(userDto);

        Response response = userResource.createUser(userDto);

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }
}