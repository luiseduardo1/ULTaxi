package ca.ulaval.glo4003.ultaxi.domain.user;

import ca.ulaval.glo4003.ultaxi.domain.user.exception.InvalidEmailAddressException;
import ca.ulaval.glo4003.ultaxi.domain.user.exception.InvalidHashingStrategyException;
import ca.ulaval.glo4003.ultaxi.domain.user.exception.InvalidPasswordException;
import ca.ulaval.glo4003.ultaxi.domain.user.exception.InvalidPhoneNumberException;
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
        willReturn(A_HASH).given(hashingStrategy).hashWithRandomSalt(anyString());
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

    @Test
    public void givenOnlyNumbersPhoneNumber_whenSetPhoneNumber_thenAcceptPhoneNumber() {
        String phoneNumber = "2342355678";

        user.setPhoneNumber(phoneNumber);

        Assert.assertEquals(user.getPhoneNumber(), phoneNumber);
    }

    @Test
    public void givenPhoneNumberWithParenthesis_whenSetPhoneNumber_thenAcceptPhoneNumber() {
        String phoneNumber = "(234)2355678";

        user.setPhoneNumber(phoneNumber);

        Assert.assertEquals(user.getPhoneNumber(), phoneNumber);
    }

    @Test
    public void givenPhoneNumberWithDashes_whenSetPhoneNumber_thenAcceptPhoneNumber() {
        String phoneNumber = "234-235-5678";

        user.setPhoneNumber(phoneNumber);

        Assert.assertEquals(user.getPhoneNumber(), phoneNumber);
    }

    @Test
    public void givenPhoneNumberWithSpaces_whenSetPhoneNumber_thenAcceptPhoneNumber() {
        String phoneNumber = "234 235 5678";

        user.setPhoneNumber(phoneNumber);

        Assert.assertEquals(user.getPhoneNumber(), phoneNumber);
    }

    @Test
    public void givenPhoneNumberWithDots_whenSetPhoneNumber_thenAcceptPhoneNumber() {
        String phoneNumber = "234.235.5678";

        user.setPhoneNumber(phoneNumber);

        Assert.assertEquals(user.getPhoneNumber(), phoneNumber);
    }

    @Test(expected = InvalidPhoneNumberException.class)
    public void givenPhoneNumberWithInvalidCentralOffice_whenSetPhoneNumber_thenThrowsInvalidPhoneNumberException() {
        String phoneNumber = "314 159 2653";

        user.setPhoneNumber(phoneNumber);
    }

    @Test(expected = InvalidPhoneNumberException.class)
    public void givenPhoneNumberWithInvalidNumberingPlanArea_whenSetPhoneNumber_thenThrowsInvalidPhoneNumberException
        () {
        String phoneNumber = "123 234 5678";

        user.setPhoneNumber(phoneNumber);
    }

    @Test(expected = InvalidPhoneNumberException.class)
    public void givenPhoneNumberWithAlphaNumericalCharacters_whenSetPhoneNumber_thenThrowsInvalidPhoneNumberException
        () {
        String phoneNumber = "1b3 2z4 56a8";

        user.setPhoneNumber(phoneNumber);
    }

    @Test(expected = InvalidPhoneNumberException.class)
    public void givenPhoneNumberWithSpecialCharacters_whenSetPhoneNumber_thenThrowsInvalidPhoneNumberException() {
        String phoneNumber = "1!3 2?4 56!8";

        user.setPhoneNumber(phoneNumber);
    }

    @Test(expected = InvalidPhoneNumberException.class)
    public void givenPhoneNumberTooLong_whenSetPhoneNumber_thenThrowsInvalidPhoneNumberException() {
        String phoneNumber = "234 235 56784";

        user.setPhoneNumber(phoneNumber);
    }

}
