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
import static org.junit.Assert.assertNull;
import static org.mockito.BDDMockito.willReturn;

@RunWith(MockitoJUnitRunner.class)
public class UserRepositoryInMemoryTest {

    private static final String A_NAME = "Ronald";
    private static final String AN_INVALID_NAME = "ronald.beaubrun@ulaval.ca";

    @Mock
    private User user;
    private UserRepository userRepository;

    @Before
    public void setUp() {
        userRepository = new UserRepositoryInMemory();

        willReturn(A_NAME).given(user).getName();
    }

    @Test
    public void givenNonExistingUser_whenFindByName_thenReturnsNull() {
        User returnedUser = userRepository.findByName(user.getName());

        assertNull(returnedUser);
    }

    @Test
    public void givenUser_whenSave_thenSavesUser() {
        userRepository.save(user);
        User savedUser = userRepository.findByName(user.getName());

        assertEquals(user, savedUser);
    }

    @Test(expected = UserAlreadyExistsException.class)
    public void givenExistingUser_whenSave_thenThrowsException() {
        userRepository.save(user);
        userRepository.save(user);
    }
}