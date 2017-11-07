package ca.ulaval.glo4003.ultaxi.api.user;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4003.ultaxi.domain.user.User;
import ca.ulaval.glo4003.ultaxi.domain.user.exception.InvalidEmailAddressException;
import ca.ulaval.glo4003.ultaxi.domain.user.exception.InvalidPasswordException;
import ca.ulaval.glo4003.ultaxi.domain.user.exception.InvalidUsernameException;
import ca.ulaval.glo4003.ultaxi.domain.user.exception.UserAlreadyExistsException;
import ca.ulaval.glo4003.ultaxi.service.user.UserAuthenticationService;
import ca.ulaval.glo4003.ultaxi.service.user.UserService;
import ca.ulaval.glo4003.ultaxi.transfer.user.UserDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ws.rs.core.Response;

@RunWith(MockitoJUnitRunner.class)
public class UserResourceImplTest {

    private static final String A_VALID_TOKEN = "Valid token";
    private static final String A_VALID_USERNAME = "username";
    private static final String AN_INVALID_EMAIL_ADDRESS = "ronald.macdonald@.ulaval.ca";

    @Mock
    private UserService userService;
    @Mock
    private UserAuthenticationService userAuthenticationService;
    @Mock
    private UserDto userDto;
    @Mock
    private User user;

    private UserResource userResource;

    @Before
    public void setUp() {
        userResource = new UserResourceImpl(userService, userAuthenticationService);
        when(userAuthenticationService.authenticateFromToken(A_VALID_TOKEN)).thenReturn(user);
        when(user.getUsername()).thenReturn(A_VALID_USERNAME);
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
    public void givenAnAuthenticatedUser_whenUpdateUser_thenReturnsOK() {
        Response response = userResource.updateUser(A_VALID_TOKEN, userDto);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void givenAUserResource_whenUpdatingUser_thenDelegateToUserService() {
        userResource.updateUser(A_VALID_TOKEN, userDto);

        verify(userService).updateUser(userDto, A_VALID_USERNAME);
    }
}



