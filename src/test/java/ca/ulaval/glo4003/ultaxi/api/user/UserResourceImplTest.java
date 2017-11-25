package ca.ulaval.glo4003.ultaxi.api.user;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;

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
    public void givenAnAuthenticatedClient_whenUpdateClient_thenReturnsOK() {
        Response response = userResource.updateClient(A_VALID_TOKEN, userDto);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void givenAUserResource_whenUpdatingClient_thenDelegateToUserService() {
        userResource.updateClient(A_VALID_TOKEN, userDto);

        verify(userService).updateClient(userDto, A_VALID_TOKEN);
    }
}



