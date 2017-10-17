package ca.ulaval.glo4003.ultaxi.infrastructure.user;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.BDDMockito.willReturn;

import ca.ulaval.glo4003.ultaxi.domain.user.User;
import ca.ulaval.glo4003.ultaxi.domain.user.UserRepository;
import ca.ulaval.glo4003.ultaxi.domain.user.exception.UserAlreadyExistsException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class UserRepositoryInMemoryTest {

    private static final String A_NAME = "ronald";
    private static final String A_DIFFERENT_CASED_NAME = "rOnAld";

    @Mock
    private User user;
    private UserRepository userRepository;

    @Before
    public void setUp() {
        userRepository = new UserRepositoryInMemory();

        willReturn(A_NAME).given(user).getUsername();
    }

    @Test
    public void givenUser_whenSave_thenUserHasSameParameters() {
        userRepository.save(user);
        User savedUser = userRepository.findByUserName(user.getUsername());

        assertEquals(user, savedUser);
    }

    @Test
    public void givenNonExistingUser_whenFindByName_thenReturnsNull() {
        User returnedUser = userRepository.findByUserName(user.getUsername());

        assertNull(returnedUser);
    }

    @Test
    public void givenUserWithDifferentNameCasing_whenFindByName_thenReturnsTheUser() {
        userRepository.save(user);

        User savedUser = userRepository.findByUserName(A_DIFFERENT_CASED_NAME);

        assertNotNull(savedUser);
    }

    @Test(expected = UserAlreadyExistsException.class)
    public void givenExistingUser_whenSave_thenThrowsException() {
        userRepository.save(user);
        userRepository.save(user);
    }
}