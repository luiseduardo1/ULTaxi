package ca.ulaval.glo4003.ws.api.user;

import ca.ulaval.glo4003.ws.api.user.dto.UserDto;
import ca.ulaval.glo4003.ws.domain.user.InvalidCredentialsException;
import ca.ulaval.glo4003.ws.domain.user.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.willReturn;

@RunWith(MockitoJUnitRunner.class)
public class UserAuthenticationResourceImplTest {

    @Mock
    private UserService userService;

    @Mock
    private UserDto userDto;

    private UserAuthenticationResource userAuthenticationResource;

    @Before
    public void setUp() throws Exception {
        userAuthenticationResource = new UserAuthenticationResourceImpl(userService);
    }

    @Test
    public void givenUserWithValidCredentials_whenAuthenticating_thenReturnsOk() {

        willReturn(true).given(userService).authenticate(userDto);
        Response response = userAuthenticationResource.authenticateUser(userDto);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void givenAnUserToAuthenticateWithBadCredentials_whenAuthenticating_thenResponseIsForbidden() {

        willReturn(false).given(userService).authenticate(userDto);
        Response response = userAuthenticationResource.authenticateUser(userDto);

        assertEquals(Response.Status.FORBIDDEN.getStatusCode(), response.getStatus());
    }
}
