package ca.ulaval.glo4003.ws.domain.user;

import org.junit.Before;
import org.junit.Test;

public class UserTest {
    private static final String AN_EMAIL_ADDRESS = "ronald.beaubrun@ulaval.ca";
    private static final String A_VALID_NAME = "Ronald Beaubrun";
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
        user.setName("      \t");
    }

    @Test(expected = InvalidUserNameException.class)
    public void givenUserWithEmailAddressAsName_whenAssigningName_thenThrowsInvalidUserNameException() {
        user.setName(AN_EMAIL_ADDRESS);
    }

    @Test
    public void givenUserWithValidName_whenAssiningName_thenNameIsAssigned() {
        user.setName(A_VALID_NAME);
    }
}
