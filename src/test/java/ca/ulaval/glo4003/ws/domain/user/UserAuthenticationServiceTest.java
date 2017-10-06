package ca.ulaval.glo4003.ws.domain.user;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.BDDMockito.willReturn;

@RunWith(MockitoJUnitRunner.class)
public class UserAuthenticationServiceTest {

    private UserAuthenticationService userAuthenticationService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private User user;

    @Mock
    private User nonExistentUser;

    private static final String A_NAME = "Ronald";
    private User userToAuthenticate;

    @Before
    public void setUp() throws Exception {
        this.userToAuthenticate = new User();
        userToAuthenticate.setName(A_NAME);
        willReturn(user).given(userRepository).findByName(A_NAME);
        userAuthenticationService = new UserAuthenticationService(userRepository);
    }

    @Test
    public void givenAUserToAuthenticate_whenAuthenticatingUser_thenUserIsAuthenticated() {
        willReturn(true).given(user).isTheSameAs(this.userToAuthenticate);

        userAuthenticationService.authenticate(userToAuthenticate);
    }

    @Test(expected = InvalidCredentialsException.class)
    public void givenAnInvalidUserToAuthenticate_whenAuthenticatingUser_thenExceptionIsThrown() {
        willReturn(false).given(user).isTheSameAs(userToAuthenticate);

        userAuthenticationService.authenticate(userToAuthenticate);
    }

    @Test(expected = InvalidCredentialsException.class)
    public void givenANonExistentUserToAuthenticate_whenAuthenticatingUser_thenExceptionIsThrown() {
        userAuthenticationService.authenticate(nonExistentUser);
    }

}
