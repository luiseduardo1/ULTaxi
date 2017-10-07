package ca.ulaval.glo4003.ultaxi.service.user;

import static org.mockito.BDDMockito.willReturn;

import ca.ulaval.glo4003.ultaxi.domain.user.User;
import ca.ulaval.glo4003.ultaxi.domain.user.UserRepository;
import ca.ulaval.glo4003.ultaxi.domain.user.exception.InvalidCredentialsException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class UserAuthenticationServiceTest {

    private static final String A_NAME = "Ronald";
    private UserAuthenticationService userAuthenticationService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private User user;
    @Mock
    private User nonExistentUser;
    private User userToAuthenticate;

    @Before
    public void setUp() throws Exception {
        this.userToAuthenticate = new User();
        userToAuthenticate.setUserName(A_NAME);
        willReturn(user).given(userRepository).findByName(userToAuthenticate.getUserName());
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
