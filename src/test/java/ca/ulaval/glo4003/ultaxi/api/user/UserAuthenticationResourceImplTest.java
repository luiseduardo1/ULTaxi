package ca.ulaval.glo4003.ultaxi.api.user;

import ca.ulaval.glo4003.ultaxi.domain.user.TokenManager;
import ca.ulaval.glo4003.ultaxi.domain.user.TokenRepository;
import ca.ulaval.glo4003.ultaxi.service.user.UserAuthenticationService;
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
public class UserAuthenticationResourceImplTest {

    private static final String AN_UNPARSED_TOKEN = "Bearer Ronald Mcdonald";
    private static final String A_TOKEN = "Ronald Mcdonald";
    private static final String AN_ID = "RONALD_MACDONALD";
    @Mock
    private UserAuthenticationService userAuthenticationService;

    @Mock
    private UserDto userDto;

    @Mock
    private TokenRepository tokenRepository;

    @Mock
    private TokenManager tokenManager;

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
