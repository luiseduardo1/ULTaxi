package ca.ulaval.glo4003.ws.infrastructure.user;

import ca.ulaval.glo4003.ws.domain.user.exception.InvalidUserNameException;
import ca.ulaval.glo4003.ws.domain.user.User;
import ca.ulaval.glo4003.ws.domain.user.exception.UserAlreadyExistsException;
import ca.ulaval.glo4003.ws.domain.user.UserRepository;
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
    private static final String AN_INVALID_NAME = "ronald.beaubrun@ulaval.ca";

    @Before
    public void setUp() throws Exception {
        userRepository = new UserRepositoryInMemory();
    }

    @Test
    public void givenUser_whenSave_thenUserHasSameParameters() {
        willReturn(A_NAME).given(user).getUserName();

        userRepository.save(user);
        User anotherUser = userRepository.findByName(user.getUserName());

        assertEquals(user, anotherUser);
    }

    @Test(expected = UserAlreadyExistsException.class)
    public void givenExistingUser_whenSave_thenThrowsException() {
        willReturn(A_NAME).given(user).getUserName();

        userRepository.save(user);
        userRepository.save(user);
    }

    @Test(expected = InvalidUserNameException.class)
    public void givenUserWithInvalidName_whenSave_thenThrowsException() {
        willReturn(AN_INVALID_NAME).given(user).getUserName();

        userRepository.save(user);
    }
}