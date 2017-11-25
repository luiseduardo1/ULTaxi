package ca.ulaval.glo4003.ultaxi.api.user;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;

import ca.ulaval.glo4003.ultaxi.service.user.UserAuthenticationService;
import ca.ulaval.glo4003.ultaxi.transfer.user.UserDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ws.rs.core.Response;

@RunWith(MockitoJUnitRunner.class)
public class UserAuthenticationResourceImplTest {

    private static final String AN_UNPARSED_TOKEN = "Bearer Ronald Mcdonald";

    @Mock
    private UserAuthenticationService userAuthenticationService;
    @Mock
    private UserDto userDto;

    private UserAuthenticationResource userAuthenticationResource;

    @Before
    public void setUp() throws Exception {
        userAuthenticationResource = new UserAuthenticationResourceImpl(userAuthenticationService);
    }

    @Test
    public void givenUserWithValidCredentials_whenAuthenticating_thenReturnsOk() {

        Response response = userAuthenticationResource.authenticateUser(userDto);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void givenToken_whenSignOut_thenReturnsResetContent() {
        Response response = userAuthenticationResource.signOut(AN_UNPARSED_TOKEN);

        assertEquals(Response.Status.RESET_CONTENT.getStatusCode(), response.getStatus());
    }

    @Test
    public void givenToken_whenSignOut_thenUserAuthenticationIsCalled() {
        userAuthenticationResource.signOut(AN_UNPARSED_TOKEN);

        verify(userAuthenticationService).deauthenticate(AN_UNPARSED_TOKEN);
    }
}
