package ca.ulaval.glo4003.ultaxi.domain.user;

import ca.ulaval.glo4003.ultaxi.domain.user.exception.InvalidEmailAddressException;
import ca.ulaval.glo4003.ultaxi.domain.user.exception.InvalidHashingStrategyException;
import ca.ulaval.glo4003.ultaxi.domain.user.exception.InvalidPasswordException;
import ca.ulaval.glo4003.ultaxi.domain.user.exception.InvalidUsernameException;
import ca.ulaval.glo4003.ultaxi.utils.hashing.HashingStrategy;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;
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

    private static final String A_VALID_USERNAME = "Ronald Macdonald";
    private static final String A_VALID_PASSWORD = "mysupersecret";
    private static final String A_VALID_EMAIL_ADDRESS = "ronald.macdonald@ulaval.ca";
    private static final String AN_INVALID_NAME = "      \t";
    private static final String AN_INVALID_PASSWORD = "    \t";
    private static final String AN_INVALID_EMAIL_ADDRESS = "ronald.macdonald@.ulaval.ca";
    private static final String A_VALID_PHONE_NUMBER = "234-235-5678";
    private static final String A_HASH = RandomStringUtils.randomAlphabetic(10);

    @Mock
    private HashingStrategy hashingStrategy;

    private User user;
    private User anotherUser;
    private PhoneNumber phoneNumber = new PhoneNumber(A_VALID_PHONE_NUMBER);

    @Before
    public void setUp() {
        user = createValidUser();
        anotherUser = createValidUser();

        willReturn(A_HASH).given(hashingStrategy).hashWithRandomSalt(anyString());
        willReturn(true).given(hashingStrategy).areEquals(anyString(), anyString());
    }

    @Test(expected = InvalidUsernameException.class)
    public void givenUserWithNullName_whenAssigningName_thenThrowsInvalidUserNameException() {
        createUserWithUsername(null);
    }

    @Test(expected = InvalidUsernameException.class)
    public void givenUserWithEmptyName_whenAssigningName_thenThrowsInvalidUserNameException() {
        createUserWithUsername(AN_INVALID_NAME);
    }

    @Test(expected = InvalidUsernameException.class)
    public void givenUserWithEmailAddressAsName_whenAssigningName_thenThrowsInvalidUserNameException() {
        createUserWithUsername(A_VALID_EMAIL_ADDRESS);
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
        createUserWithUsername(A_VALID_USERNAME);
    }

    @Test
    public void givenTwoUsersWithSameNameAndPasswords_whenCheckingIfTheyAreTheSame_thenReturnsTrue() {
        assertTrue(user.areValidCredentials(anotherUser.getUsername(), A_VALID_PASSWORD));
    }

    @Test
    public void givenAUserAndNullCredentials_whenCheckingIfTheCredentialsAreValid_thenReturnsFalse() {
        assertFalse(user.areValidCredentials(null, null));
    }

    @Test
    public void givenAValidPhoneNumber_whenSetPhoneNumber_thenPhoneNumberIsAssigned() {
        user.setPhoneNumber(phoneNumber);

        Assert.assertEquals(user.getPhoneNumber(), phoneNumber);
    }

    private User createValidUser() {
        return createUserWithUsername(A_VALID_USERNAME);
    }

    private User createUserWithUsername(String username) {
        return new User(username, A_VALID_PASSWORD, phoneNumber, A_VALID_EMAIL_ADDRESS, hashingStrategy) {
            @Override
            public Role getRole() {
                return null;
            }
        };
    }
}
