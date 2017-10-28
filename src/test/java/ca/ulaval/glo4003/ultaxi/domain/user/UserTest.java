package ca.ulaval.glo4003.ultaxi.domain.user;

import ca.ulaval.glo4003.ultaxi.domain.user.exception.InvalidEmailAddressException;
import ca.ulaval.glo4003.ultaxi.domain.user.exception.InvalidHashingStrategyException;
import ca.ulaval.glo4003.ultaxi.domain.user.exception.InvalidPasswordException;
import ca.ulaval.glo4003.ultaxi.domain.user.exception.InvalidUsernameException;
import ca.ulaval.glo4003.ultaxi.utils.hashing.HashingStrategy;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Matchers.anyString;

@RunWith(MockitoJUnitRunner.class)
public class UserTest {

    private static final String AN_EMAIL_ADDRESS = "ronald.macdonald@ulaval.ca";
    private static final String AN_INVALID_EMAIL_ADDRESS = "ronald.macdonald@.ulaval.ca";
    private static final String A_VALID_USERNAME = "Ronald Macdonald";
    private static final String A_VALID_PASSWORD = "mysupersecret";
    private static final String AN_INVALID_NAME = "      \t";
    private static final String AN_INVALID_PASSWORD = "    \t";
    private static final String A_HASH = RandomStringUtils.randomAlphabetic(10);
    @Mock
    private HashingStrategy hashingStrategy;
    private User user;

    @Before
    public void setUp() {
        user = new User();
    }

    @Test(expected = InvalidUsernameException.class)
    public void givenUserWithNullName_whenAssigningName_thenThrowsInvalidUserNameException() {
        user.setUsername(null);
    }

    @Test(expected = InvalidUsernameException.class)
    public void givenUserWithEmptyName_whenAssigningName_thenThrowsInvalidUserNameException() {
        user.setUsername(AN_INVALID_NAME);
    }

    @Test(expected = InvalidUsernameException.class)
    public void givenUserWithEmailAddressAsName_whenAssigningName_thenThrowsInvalidUserNameException() {
        user.setUsername(AN_EMAIL_ADDRESS);
    }

    @Test(expected = InvalidEmailAddressException.class)
    public void givenUserWithInvalidEmailAddress_whenAssigningEmailAddress_thenThrowsInvalidEmailAddressException() {
        user.setEmailAddress(AN_INVALID_EMAIL_ADDRESS);
    }

    @Test(expected = InvalidPasswordException.class)
    public void givenUserWithEmptyPassword_whenAssigningPassword_thenThrowsInvalidPasswordException() {
        user.setPassword(AN_INVALID_PASSWORD, hashingStrategy);
    }

    @Test(expected = InvalidPasswordException.class)
    public void givenUserWithNullPassword_whenAssigningPassword_thenThrowsInvalidPasswordException() {
        user.setPassword(null, hashingStrategy);
    }

    @Test(expected = InvalidHashingStrategyException.class)
    public void givenUserWithNullHashingStrategy_whenAssigningPassword_thenThrowsInvalidHashingStrategyException() {
        user.setPassword(A_VALID_PASSWORD, null);
    }

    @Test
    public void givenUserWithValidName_whenAssigningName_thenNameIsAssigned() {
        user.setUsername(A_VALID_USERNAME);
    }

    @Test
    public void givenTwoUsersWithSameNameAndPasswords_whenCheckingIfTheyAreTheSame_thenReturnsTrue() {
        willReturn(A_HASH).given(hashingStrategy).hash(anyString());
        willReturn(true).given(hashingStrategy).areEquals(anyString(), anyString());
        User anotherUser = new User();
        anotherUser.setUsername(A_VALID_USERNAME);
        anotherUser.setPassword(A_VALID_PASSWORD, hashingStrategy);
        user.setUsername(A_VALID_USERNAME);
        user.setPassword(A_VALID_PASSWORD, hashingStrategy);

        assertTrue(user.areCredentialsValid(anotherUser.getUsername(), A_VALID_PASSWORD));
    }

    @Test
    public void givenAUserAndNullCredentials_whenCheckingIfTheCredentialsAreValid_thenReturnsFalse() {
        assertFalse(user.areCredentialsValid(null, null));
    }
}
