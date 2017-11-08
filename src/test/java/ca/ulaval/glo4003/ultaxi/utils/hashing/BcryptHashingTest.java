package ca.ulaval.glo4003.ultaxi.utils.hashing;

import org.junit.Test;

import static org.junit.Assert.*;

public class BcryptHashingTest {

    private static final String A_VALUE = "ro|\\|/-\\|8/-\\|8run";
    private final HashingStrategy hashingStrategy = new BcryptHashing();

    @Test
    public void givenANullValue_whenHashing_thenReturnsNull() {
        String hashedValue = hashingStrategy.hash(null);

        assertNull(hashedValue);
    }

    @Test
    public void givenAValue_whenHashing_thenReturnsHashedValue() {
        String hashedValue = hashingStrategy.hash(A_VALUE);

        assertNotEquals(A_VALUE, hashedValue);
    }

    @Test
    public void givenAValue_whenHashingTwice_thenHashesAreTheSame() {
        String firstHashedValue = hashingStrategy.hash(A_VALUE);
        String secondHashedValue = hashingStrategy.hash(A_VALUE);

        assertEquals(firstHashedValue, secondHashedValue);
    }

    @Test
    public void givenANullValue_whenHashingWithRandomSalt_thenReturnsNull() {
        String hashedValue = hashingStrategy.hashWithRandomSalt(null);

        assertNull(hashedValue);
    }

    @Test
    public void givenAValue_whenHashingWithRandomSalt_thenReturnsHashedValue() {
        String hashedValue = hashingStrategy.hashWithRandomSalt(A_VALUE);

        assertNotEquals(A_VALUE, hashedValue);
    }

    @Test
    public void givenAValue_whenHashingWithRandomSaltTwice_thenHashesAreDifferent() {
        String firstHashedValue = hashingStrategy.hashWithRandomSalt(A_VALUE);
        String secondHashedValue = hashingStrategy.hashWithRandomSalt(A_VALUE);

        assertNotEquals(firstHashedValue, secondHashedValue);
    }

    @Test
    public void givenAHashedValue_whenCheckingIfEqualToTheTheUnhashedValue_thenReturnsTrue() {
        String hashedValue = hashingStrategy.hash(A_VALUE);

        boolean areEquals = hashingStrategy.areEquals(A_VALUE, hashedValue);

        assertTrue(areEquals);
    }

    @Test
    public void givenANullValue_whenCheckingIfEqualToHashedValue_thenReturnsFalse() {
        String hashedValue = hashingStrategy.hash(A_VALUE);

        boolean areEquals = hashingStrategy.areEquals(null, hashedValue);

        assertFalse(areEquals);
    }
}