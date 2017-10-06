package ca.ulaval.glo4003.ws.api.user;

import ca.ulaval.glo4003.ws.api.user.dto.UserDto;
import ca.ulaval.glo4003.ws.domain.user.InvalidCredentialsException;
import ca.ulaval.glo4003.ws.domain.user.TokenManager;
import ca.ulaval.glo4003.ws.domain.user.UserService;
import ca.ulaval.glo4003.ws.infrastructure.user.TokenRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class UserAuthenticationResourceImplTest {

    private static final String A_TOKEN = "Ronald Beabrun";
    private static final String AN_ID = "RONALD_BEAUBRUN";
    @Mock
    private UserService userService;

    @Mock
    private UserDto userDto;

    @Mock
    private TokenRepository tokenRepository;

    @Mock
    private TokenManager tokenManager;

    private UserAuthenticationResource userAuthenticationResource;

    @Before
    public void setUp() throws Exception {
        userAuthenticationResource = new UserAuthenticationResourceImpl(userService,
            tokenRepository, tokenManager);
    }

    @Test
    public void givenUserWithValidCredentials_whenAuthenticating_thenReturnsOk() {

        Response response = userAuthenticationResource.authenticateUser(userDto);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void givenAnUserToAuthenticateWithBadCredentials_whenAuthenticating_thenResponseIsForbidden() {

        doThrow(new InvalidCredentialsException("")).when(userService).authenticate(userDto);
        Response response = userAuthenticationResource.authenticateUser(userDto);

        assertEquals(Response.Status.FORBIDDEN.getStatusCode(), response.getStatus());
    }

    @Test
    public void givenToken_whenSignOut_thenReturnsResetContent() {
        Response response = userAuthenticationResource.signOut(A_TOKEN);

        assertEquals(Response.Status.RESET_CONTENT.getStatusCode(), response.getStatus());
    }

    @Test
    public void givenToken_whenSignOut_thenTokenIsDeleted() {
        willReturn(AN_ID).given(tokenManager).getTokenId(A_TOKEN);

        userAuthenticationResource.signOut(A_TOKEN);

        verify(tokenRepository).delete(AN_ID);
    }
}
