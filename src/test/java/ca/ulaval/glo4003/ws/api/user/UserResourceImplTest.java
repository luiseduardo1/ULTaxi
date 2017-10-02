package ca.ulaval.glo4003.ws.api.user;

import ca.ulaval.glo4003.ws.api.user.dto.UserDto;
import ca.ulaval.glo4003.ws.domain.user.exception.InvalidUserNameException;
import ca.ulaval.glo4003.ws.domain.user.exception.UserAlreadyExistsException;
import ca.ulaval.glo4003.ws.domain.user.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.willThrow;


@RunWith(MockitoJUnitRunner.class)
public class UserResourceImplTest {

    @Mock
    private UserService userService;
    @Mock
    private UserDto userDto;

    private UserResource userResource;

    @Before
    public void setUp() throws Exception {
        userResource = new UserResourceImpl(userService);
    }

    @Test
    public void givenUserWithValidName_whenCreateUser_thenReturnsOk() {
        Response response = userResource.createUser(userDto);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
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
}