package ca.ulaval.glo4003.ultaxi.api.user;

import ca.ulaval.glo4003.ultaxi.domain.user.User;
import ca.ulaval.glo4003.ultaxi.service.user.UserAuthenticationService;
import ca.ulaval.glo4003.ultaxi.service.user.UserService;
import ca.ulaval.glo4003.ultaxi.transfer.user.UserDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;

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
    public void givenAnAuthenticatedUser_updateClient_thenReturnsOK() {
        Response response = userResource.updateClient(A_VALID_TOKEN, userDto);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void givenAUserResource_whenUpdatingClient_thenDelegateToUserService() {
        userResource.updateClient(A_VALID_TOKEN, userDto);

        verify(userService).updateClient(userDto, A_VALID_TOKEN);
    }

    /*@Test
    public void givenAnEmptyPassword_whenUpdatingClient_thenReturnsBadRequest() {
        when(userDto.getPassword()).thenReturn("");
        willThrow(new InvalidPasswordException("User has an invalid password."))
            .given(userService)
            .updateClient(userDto, A_VALID_TOKEN);

        Response response = userResource.updateClient(A_VALID_TOKEN, userDto);

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }    */

    /*@Test
    public void givenAnInvalidEmail_whenUpdatingClient_thenReturnsBadRequest() {
        when(userDto.getEmail()).thenReturn(AN_INVALID_EMAIL_ADDRESS);
        willThrow(new InvalidEmailAddressException("User has an invalid email address."))
            .given(userService)
            .updateClient(userDto, A_VALID_TOKEN);

        Response response = userResource.updateClient(A_VALID_TOKEN, userDto);

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    } */
}



