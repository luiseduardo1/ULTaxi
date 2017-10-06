package ca.ulaval.glo4003.ws.domain.user;

import ca.ulaval.glo4003.ws.infrastructure.user.BcryptHashing;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class UserTest {

    private static final String AN_EMAIL_ADDRESS = "ronald.beaubrun@ulaval.ca";
    private static final String A_VALID_NAME = "Ronald Beaubrun";
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
        user.setName(null);
    }

    @Test(expected = InvalidUserNameException.class)
    public void givenUserWithEmptyName_whenAssigningName_thenThrowsInvalidUserNameException() {
        user.setName(AN_INVALID_NAME);
    }

    @Test(expected = InvalidUserNameException.class)
    public void givenUserWithEmailAddressAsName_whenAssigningName_thenThrowsInvalidUserNameException() {
        user.setName(AN_EMAIL_ADDRESS);
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
        user.setName(A_VALID_NAME);
    }

    @Test
    public void givenTwoUsersWithSameNameAndPasswords_whenCheckingIfTheyAreTheSame_thenReturnsTrue() {
        User anotherUser = new User();
        anotherUser.setName(A_VALID_NAME);
        anotherUser.setPassword(A_VALID_PASSWORD);
        anotherUser.setHashingStrategy(A_HASHING_STRATEGY);
        user.setName(A_VALID_NAME);
        user.setPassword(A_VALID_PASSWORD);
        user.setHashingStrategy(A_HASHING_STRATEGY);

        assertTrue(user.isTheSameAs(anotherUser));
    }

    @Test
    public void givenAUserAndAnotherNullUser_whenCheckingIfTheyAreTheSame_thenReturnsFalse() {
        assertFalse(user.isTheSameAs(null));
    }
}
