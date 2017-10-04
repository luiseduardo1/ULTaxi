package ca.ulaval.glo4003.ws.domain.user;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class UserTest {
    private static final String AN_EMAIL_ADDRESS = "ronald.beaubrun@ulaval.ca";
    private static final String A_VALID_NAME = "Ronald Beaubrun";
    private User user;

    @Before
    public void setUp() {
        user = new User();
    }

    @Test
    public void givenUserWithNullName_whenIsValid_thenIsFalse() {
        user.setName(null);

        boolean isValid = user.isValid();

        assertFalse(isValid);
    }

    @Test
    public void givenUserWithEmptyName_whenIsValid_thenIsFalse() {
        user.setName("      \t");

        boolean isValid = user.isValid();

        assertFalse(isValid);
    }

    @Test
    public void givenUserWithEmailAddressAsName_whenIsValid_thenIsFalse() {
        user.setName(AN_EMAIL_ADDRESS);

        boolean isValid = user.isValid();

        assertFalse(isValid);
    }

    @Test
    public void givenUserWithValidName_whenIsValid_thenIsTrue() {
        user.setName(A_VALID_NAME);

        boolean isValid = user.isValid();

        assertTrue(isValid);
    }
}
