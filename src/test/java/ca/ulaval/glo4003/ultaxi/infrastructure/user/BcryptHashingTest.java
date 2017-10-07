package ca.ulaval.glo4003.ultaxi.infrastructure.user;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import ca.ulaval.glo4003.ultaxi.domain.user.HashingStrategy;
import org.junit.Test;

public class BcryptHashingTest {

    private static final String A_PASSWORD = "ro|\\|/-\\|8/-\\|8run";
    private final HashingStrategy hashingStrategy = new BcryptHashing();

    @Test
    public void givenANullPassword_whenHashing_thenReturnsNull() {
        String hashedPassword = hashingStrategy.hash(null);

        assertNull(hashedPassword);
    }

    @Test
    public void givenAPassword_whenHashing_thenReturnsHashedPassword() {
        String hashedPassword = hashingStrategy.hash(A_PASSWORD);

        assertNotEquals(A_PASSWORD, hashedPassword);
    }

    @Test
    public void givenAHashedPassword_whenCheckingIfEqualToTheTheUnhashedPassword_thenReturnsTrue() {
        String hashedPassword = hashingStrategy.hash(A_PASSWORD);

        boolean areEquals = hashingStrategy.areEquals(A_PASSWORD, hashedPassword);

        assertTrue(areEquals);
    }

    @Test
    public void givenANullPassword_whenCheckingIfEqualToHashedPassword_thenReturnsFalse() {
        String hashedPassword = hashingStrategy.hash(A_PASSWORD);

        boolean areEquals = hashingStrategy.areEquals(null, hashedPassword);

        assertFalse(areEquals);
    }
}