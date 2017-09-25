package ca.ulaval.glo4003.ws.infrastructure.user;

import ca.ulaval.glo4003.ws.domain.user.InvalidUserNameException;
import ca.ulaval.glo4003.ws.domain.user.User;
import ca.ulaval.glo4003.ws.domain.user.UserAlreadyExistsException;
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
    private static final String AN_ID = "RONALD_BEAUBRUN";
    private static final String A_NAME = "Ronald";

    @Before
    public void setUp() throws Exception {
        userRepository = new UserRepositoryInMemory();
    }

    @Test
    public void givenUser_whenSave_thenUserHasSameParameters() {
        willReturn(AN_ID).given(user).getId();
        willReturn(A_NAME).given(user).getName();

        userRepository.save(user);
        User anotherUser = userRepository.findById(user.getId());

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
        willReturn("ronald.beaubrun@ulaval.ca").given(user).getName();

        userRepository.save(user);
    }
}