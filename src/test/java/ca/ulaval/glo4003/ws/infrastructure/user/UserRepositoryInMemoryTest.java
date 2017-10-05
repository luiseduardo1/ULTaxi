package ca.ulaval.glo4003.ws.infrastructure.user;

import ca.ulaval.glo4003.ws.domain.user.InvalidUserNameException;
import ca.ulaval.glo4003.ws.domain.user.User;
import ca.ulaval.glo4003.ws.domain.user.UserAlreadyExistsException;
import ca.ulaval.glo4003.ws.domain.user.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(MockitoJUnitRunner.class)
public class UserRepositoryInMemoryTest {

    private static final String A_NAME = "Ronald";
    private static final String AN_INVALID_NAME = "ronald.beaubrun@ulaval.ca";

    private User user;
    private UserRepository userRepository;

    @Before
    public void setUp() throws Exception {
        userRepository = new UserRepositoryInMemory();
        user = new User();
    }

    @Test
    public void givenInexistingUser_whenFindByName_thenReturnsNull() {
        user.setName(A_NAME);

        assertNull(userRepository.findByName(user.getName()));
    }

    @Test
    public void givenUser_whenSave_thenUserHasSameParameters() {
        user.setName(A_NAME);

        userRepository.save(user);
        User anotherUser = userRepository.findByName(user.getName());

        assertEquals(user, anotherUser);
    }

    @Test(expected = UserAlreadyExistsException.class)
    public void givenExistingUser_whenSave_thenThrowsException() {
        user.setName(A_NAME);

        userRepository.save(user);
        userRepository.save(user);
    }

    @Test(expected = InvalidUserNameException.class)
    public void givenUserWithInvalidName_whenSave_thenThrowsException() {
        user.setName(AN_INVALID_NAME);

        userRepository.save(user);
    }
}