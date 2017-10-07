package ca.ulaval.glo4003.ultaxi.domain.user;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import ca.ulaval.glo4003.ultaxi.domain.user.exception.InvalidPasswordException;
import ca.ulaval.glo4003.ultaxi.domain.user.exception.InvalidUserNameException;
import ca.ulaval.glo4003.ultaxi.infrastructure.user.BcryptHashing;
import org.junit.Before;
import org.junit.Test;

public class UserTest {

    private static final String AN_EMAIL_ADDRESS = "ronald.beaubrun@ulaval.ca";
    private static final String A_VALID_USERNAME = "Ronald Beaubrun";
    private static final String A_VALID_PASSWORD = "mysupersecret";
    private static final String AN_INVALID_NAME = "      \t";
    private static final String AN_INVALID_PASSWORD = "    \t";
    private static final HashingStrategy A_HASHING_STRATEGY = new BcryptHashing();
    private User user;

    @Before
    public void setUp() {
        user = new User();
    }

    @Test(expected = InvalidUserNameException.class)
    public void givenUserWithNullName_whenAssigningName_thenThrowsInvalidUserNameException() {
        user.setUserName(null);
    }

    @Test(expected = InvalidUserNameException.class)
    public void givenUserWithEmptyName_whenAssigningName_thenThrowsInvalidUserNameException() {
        user.setUserName(AN_INVALID_NAME);
    }

    @Test(expected = InvalidUserNameException.class)
    public void givenUserWithEmailAddressAsName_whenAssigningName_thenThrowsInvalidUserNameException() {
        user.setUserName(AN_EMAIL_ADDRESS);
    }

    @Test(expected = InvalidPasswordException.class)
    public void givenUserWithEmptyPassword_whenAssigningPassword_thenThrowsInvalidPasswordException() {
        user.setPassword(AN_INVALID_PASSWORD);
    }

    @Test(expected = InvalidPasswordException.class)
    public void givenUserWithNullPassword_whenAssigningPassword_thenThrowsInvalidPasswordException() {
        user.setPassword(null);
    }

    @Test
    public void givenUserWithValidName_whenAssigningName_thenNameIsAssigned() {
        user.setUserName(A_VALID_USERNAME);
    }

    @Test
    public void givenTwoUsersWithSameNameAndPasswords_whenCheckingIfTheyAreTheSame_thenReturnsTrue() {
        User anotherUser = new User();
        anotherUser.setUserName(A_VALID_USERNAME);
        anotherUser.setPassword(A_VALID_PASSWORD);
        anotherUser.setHashingStrategy(A_HASHING_STRATEGY);
        user.setUserName(A_VALID_USERNAME);
        user.setPassword(A_VALID_PASSWORD);
        user.setHashingStrategy(A_HASHING_STRATEGY);

        assertTrue(user.isTheSameAs(anotherUser));
    }

    @Test
    public void givenAUserAndAnotherNullUser_whenCheckingIfTheyAreTheSame_thenReturnsFalse() {
        assertFalse(user.isTheSameAs(null));
    }
}
