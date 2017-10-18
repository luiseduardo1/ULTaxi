package ca.ulaval.glo4003.ultaxi.service.user;

import ca.ulaval.glo4003.ultaxi.domain.user.TokenManager;
import ca.ulaval.glo4003.ultaxi.domain.user.TokenRepository;
import ca.ulaval.glo4003.ultaxi.domain.user.User;
import ca.ulaval.glo4003.ultaxi.domain.user.UserRepository;
import ca.ulaval.glo4003.ultaxi.domain.user.exception.InvalidCredentialsException;
import ca.ulaval.glo4003.ultaxi.transfer.user.UserAssembler;
import ca.ulaval.glo4003.ultaxi.transfer.user.UserDto;
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

    private static final String A_NAME = "Ronald";
    private static final String A_PASSWORD = "Beaubrun";
    private static final String A_TOKEN = "Ronald Mcdonald";
    private static final String AN_UNPARSED_TOKEN = "Bearer Ronald Mcdonald";
    private static final String AN_ID = "RONALD_MACDONALD";
    private UserAuthenticationService userAuthenticationService;
    @Mock
    private TokenRepository tokenRepository;
    @Mock
    private TokenManager tokenManager;
    @Mock
    private UserRepository userRepository;
    @Mock
    private User anotherUser;
    @Mock
    private User user;
    @Mock
    private UserDto nonExistentUser;
    @Mock
    private UserAssembler userAssembler;
    private UserDto userToAuthenticate;

    @Before
    public void setUp() throws Exception {
        this.userToAuthenticate = new UserDto();
        userToAuthenticate.setUserName(A_NAME);
        userToAuthenticate.setPassword(A_PASSWORD);
        willReturn(user).given(userRepository).findByUsername(A_NAME.trim().toLowerCase());
        willReturn(anotherUser).given(userAssembler).create(any(UserDto.class));
        userAuthenticationService = new UserAuthenticationService(userRepository, userAssembler, tokenManager, tokenRepository);
    }

    @Test
    public void givenAUserToAuthenticate_whenAuthenticatingUser_thenUserIsAuthenticated() {
        willReturn(A_NAME.trim().toLowerCase()).given(anotherUser).getUsername();
        willReturn(true).given(user).areCredentialsValid(anyString(), anyString());

        userAuthenticationService.authenticate(userToAuthenticate);
    }

    @Test(expected = InvalidCredentialsException.class)
    public void givenAnInvalidUserToAuthenticate_whenAuthenticatingUser_thenExceptionIsThrown() {
        willReturn(A_NAME.trim().toLowerCase()).given(anotherUser).getUsername();
        willReturn(false).given(user).areCredentialsValid(anyString(), anyString());

        userAuthenticationService.authenticate(userToAuthenticate);
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

}
