package ca.ulaval.glo4003.ultaxi.service.user;

import ca.ulaval.glo4003.ultaxi.domain.user.TokenManager;
import ca.ulaval.glo4003.ultaxi.domain.user.TokenRepository;
import ca.ulaval.glo4003.ultaxi.domain.user.UserRepository;
import ca.ulaval.glo4003.ultaxi.domain.user.client.Client;
import ca.ulaval.glo4003.ultaxi.domain.user.exception.InvalidCredentialsException;
import ca.ulaval.glo4003.ultaxi.transfer.user.AuthenticationDto;
import ca.ulaval.glo4003.ultaxi.transfer.user.client.ClientAssembler;
import ca.ulaval.glo4003.ultaxi.transfer.user.client.ClientDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class UserAuthenticationServiceTest {

    private static final String A_USERNAME = "Ronald";
    private static final String A_PASSWORD = "Beaubrun";
    private static final String A_TOKEN = "Ronald Mcdonald";
    private static final String AN_UNPARSED_TOKEN = "Bearer Ronald Mcdonald";
    private static final String AN_ID = "RONALD_MACDONALD";

    @Mock
    private TokenRepository tokenRepository;
    @Mock
    private TokenManager tokenManager;
    @Mock
    private UserRepository userRepository;
    @Mock
    private Client anotherUser;
    @Mock
    private Client user;
    @Mock
    private ClientDto nonExistentUser;
    @Mock
    private ClientAssembler clientAssembler;

    private AuthenticationDto userAuthenticationDto;
    private UserAuthenticationService userAuthenticationService;

    @Before
    public void setUp() {
        this.userAuthenticationDto = new ClientDto();
        userAuthenticationDto.setUsername(A_USERNAME);
        userAuthenticationDto.setPassword(A_PASSWORD);
        willReturn(user).given(userRepository).findByUsername(A_USERNAME);
        willReturn(anotherUser).given(clientAssembler).create(any(ClientDto.class));
        userAuthenticationService = new UserAuthenticationService(userRepository, clientAssembler, tokenManager,
                                                                  tokenRepository);
    }

    @Test
    public void givenAUserToAuthenticate_whenAuthenticatingUser_thenUserIsAuthenticated() {
        willReturn(A_USERNAME.trim().toLowerCase()).given(anotherUser).getUsername();
        willReturn(true).given(user).areValidCredentials(anyString(), anyString());

        userAuthenticationService.authenticate(userAuthenticationDto);
    }

    @Test(expected = InvalidCredentialsException.class)
    public void givenAnInvalidUserToAuthenticate_whenAuthenticatingUser_thenExceptionIsThrown() {
        willReturn(A_USERNAME.trim().toLowerCase()).given(anotherUser).getUsername();
        willReturn(false).given(user).areValidCredentials(anyString(), anyString());

        userAuthenticationService.authenticate(userAuthenticationDto);
    }

    @Test(expected = InvalidCredentialsException.class)
    public void givenANonExistentUserToAuthenticate_whenAuthenticatingUser_thenExceptionIsThrown() {
        userAuthenticationService.authenticate(nonExistentUser);
    }

    @Test
    public void givenToken_whenDeauthenticate_thenTokenIsDeleted() {
        willReturn(AN_ID).given(tokenManager).getTokenId(A_TOKEN);

        userAuthenticationService.deauthenticate(AN_UNPARSED_TOKEN);

        verify(tokenRepository).delete(AN_ID);
    }

    @Test
    public void givenAUserToken_whenGetUserFromToken_thenDelegateToTokenManager() {
        userAuthenticationService.getUserFromToken(A_TOKEN);

        verify(tokenManager).getUsername(A_TOKEN);
    }

    @Test
    public void givenAUserToken_whenGetUserFromToken_thenDelegateToUserRepository() {
        willReturn(A_USERNAME).given(tokenManager).getUsername(A_TOKEN);

        userAuthenticationService.getUserFromToken(A_TOKEN);

        verify(userRepository).findByUsername(A_USERNAME);
    }
}
