package ca.ulaval.glo4003.ultaxi.infrastructure.user;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.BDDMockito.willReturn;

import ca.ulaval.glo4003.ultaxi.domain.user.User;
import ca.ulaval.glo4003.ultaxi.domain.user.UserRepository;
import ca.ulaval.glo4003.ultaxi.domain.user.exception.NonExistentUserException;
import ca.ulaval.glo4003.ultaxi.domain.user.exception.UserAlreadyExistsException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class UserRepositoryInMemoryTest {

    private static final String A_USERNAME = "ronald";
    private static final String A_DIFFERENT_CASED_USERNAME = "rOnAld";
    private static final String ORIGINAL_EMAIL_ADDRESS = "ronald.macdonald@ulaval.ca";
    private static final String UPDATED_EMAIL_ADDRESS = "ro.mac@ulaval.ca";

    @Mock
    private User user;
    private UserRepository userRepository;

    @Before
    public void setUp() {
        userRepository = new UserRepositoryInMemory();

        willReturn(A_USERNAME).given(user).getUsername();
    }

    @Test
    public void givenUser_whenSave_thenUserHasSameParameters() {
        userRepository.save(user);
        User savedUser = userRepository.findByUsername(user.getUsername());

        assertEquals(user, savedUser);
    }

    @Test
    public void givenNonExistingUser_whenFindByUsername_thenReturnsNull() {
        User returnedUser = userRepository.findByUsername(user.getUsername());

        assertNull(returnedUser);
    }

    @Test
    public void givenUserWithDifferentNameCasing_whenFindByUsername_thenReturnsTheUser() {
        userRepository.save(user);

        User savedUser = userRepository.findByUsername(A_DIFFERENT_CASED_USERNAME);

        assertNotNull(savedUser);
    }

    @Test(expected = UserAlreadyExistsException.class)
    public void givenExistingUser_whenSave_thenThrowsException() {
        userRepository.save(user);
        userRepository.save(user);
    }

    @Test
    public void givenUserToUpdate_whenUpdatingUser_thenNoExceptionIsThrown() {
        userRepository.save(user);
        userRepository.update(user);
    }

    @Test(expected = NonExistentUserException.class)
    public void givenNonExistentUserToUpdate_whenUpdatingUser_thenThrowsException() {
        userRepository.update(user);
    }

    @Test
    public void givenExistingUser_whenUpdate_thenUserHasUpdatedParameters() {
        user.setUsername(A_USERNAME);
        user.setEmailAddress(ORIGINAL_EMAIL_ADDRESS);
        User sameUserWithAnotherEmailAddress = user;
        sameUserWithAnotherEmailAddress.setEmailAddress(UPDATED_EMAIL_ADDRESS);

        userRepository.save(user);
        userRepository.update(sameUserWithAnotherEmailAddress);

        User updatedUser = userRepository.findByUsername(user.getUsername());
        assertEquals(user.getUsername(), updatedUser.getUsername());
        assertEquals(sameUserWithAnotherEmailAddress.getEmailAddress(), updatedUser.getEmailAddress());
    }
}