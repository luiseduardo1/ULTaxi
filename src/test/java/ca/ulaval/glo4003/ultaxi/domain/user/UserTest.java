package ca.ulaval.glo4003.ultaxi.domain.user;

import ca.ulaval.glo4003.ultaxi.domain.user.exception.InvalidUserNameException;
import org.junit.Before;
import org.junit.Test;

public class UserTest {

    private static final String AN_EMAIL_ADDRESS = "ronald.beaubrun@ulaval.ca";
    private static final String A_VALID_NAME = "Ronald Beaubrun";
    private static final String AN_INVALID_NAME = "      \t";
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

    @Test
    public void givenUserWithValidName_whenAssiningName_thenNameIsAssigned() {
        user.setUserName(A_VALID_NAME);
    }
}
