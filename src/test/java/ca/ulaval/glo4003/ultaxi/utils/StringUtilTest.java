package ca.ulaval.glo4003.ultaxi.utils;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class StringUtilTest {

    private static final String AN_EMPTY_STRING = "";
    private static final String A_VALID_STRING = "hOmard";
    private static final String A_SINGLE_LETTER = "a";

    @Test(expected = NullPointerException.class)
    public void givenNullString_whenCapitalizing_thenThrowsNullPointerException() {
        StringUtil.capitalize(null);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void givenEmptyString_whenCapitalizing_thenThrowsIndexOutOfBoundsException() {
        StringUtil.capitalize(AN_EMPTY_STRING);
    }

    @Test
    public void givenAValidString_whenCapitalizing_thenReturnsCapitalizedString() {
        String capitalizedString = StringUtil.capitalize(A_VALID_STRING);

        assertEquals("Homard", capitalizedString);
    }

    @Test
    public void givenSingleLetterString_whenCapitalizing_thenReturnsCapitalizedLetter() {
        String capitalizedLetter = StringUtil.capitalize(A_SINGLE_LETTER);

        assertEquals("A", capitalizedLetter);
    }
}