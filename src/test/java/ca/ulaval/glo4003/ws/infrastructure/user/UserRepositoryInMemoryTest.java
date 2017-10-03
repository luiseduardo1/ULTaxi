package ca.ulaval.glo4003.ws.infrastructure.user;

import ca.ulaval.glo4003.ws.domain.user.InvalidCredentialsException;
import ca.ulaval.glo4003.ws.domain.user.InvalidUserNameException;
import ca.ulaval.glo4003.ws.domain.user.User;
import ca.ulaval.glo4003.ws.domain.user.UserAlreadyExistsException;
import ca.ulaval.glo4003.ws.domain.user.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.willReturn;

@RunWith(MockitoJUnitRunner.class)
public class UserRepositoryInMemoryTest {

    @Mock
    private User user;
    private UserRepository userRepository;
    private static final String A_NAME = "Ronald";
    private static final String A_PASSWORD = "Be@ubrun123";
    private static final String AN_INVALID_NAME = "ronald.beaubrun@ulaval.ca";
    private static final String AN_INVALID_PASSWORD = "";

    @Before
    public void setUp() throws Exception {
        userRepository = new UserRepositoryInMemory();
    }

    @Test
    public void givenUser_whenSave_thenUserHasSameParameters() {
        willReturn(A_NAME).given(user).getName();

        userRepository.save(user);
        User anotherUser = userRepository.findByName(user.getName());

        assertEquals(user, anotherUser);
    }

    @Test(expected = UserAlreadyExistsException.class)
    public void givenExistingUser_whenSave_thenThrowsException() {
        willReturn(A_NAME).given(user).getName();

        userRepository.save(user);
        userRepository.save(user);
    }

    @Test(expected = InvalidUserNameException.class)
    public void givenUserWithInvalidName_whenSave_thenThrowsException() {
        willReturn(AN_INVALID_NAME).given(user).getName();

        userRepository.save(user);
    }

    @Test
    public void givenAnUserToAuthenticate_whenAuthenticatingUser_thenUserIsAuthenticated() {
        User user = new User();
        user.setName(A_NAME);
        user.setPassword(A_PASSWORD);
        userRepository.save(user);

        Assert.assertTrue(userRepository.authenticate(user));
    }

    @Test(expected = InvalidCredentialsException.class)
    public void givenAnUserToAuthenticateWithBadCredentials_whenAuthenticatingUser_thenThrowsException() {

        User userToAuthenticate = new User();
        userToAuthenticate.setName(A_NAME);
        userToAuthenticate.setPassword(A_PASSWORD);
        User userBadPassword = new User();
        userBadPassword.setName(A_NAME);
        userBadPassword.setPassword(AN_INVALID_PASSWORD);

        userRepository.save(userToAuthenticate);
        userRepository.authenticate(userBadPassword);
    }
}