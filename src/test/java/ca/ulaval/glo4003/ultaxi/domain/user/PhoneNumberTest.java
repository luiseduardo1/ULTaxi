package ca.ulaval.glo4003.ultaxi.domain.user;

import ca.ulaval.glo4003.ultaxi.domain.user.exception.InvalidPhoneNumberException;
import org.junit.Test;

public class PhoneNumberTest {

    @Test
    public void givenOnlyNumbersPhoneNumber_whenCreatePhoneNumber_thenNoExceptionIsThrown() {
        String phoneNumber = "2342355678";

        new PhoneNumber(phoneNumber);
    }

    @Test
    public void givenPhoneNumberWithParenthesis_whenCreatePhoneNumber_thenNoExceptionIsThrown() {
        String phoneNumber = "(234)2355678";

        new PhoneNumber(phoneNumber);
    }

    @Test
    public void givenPhoneNumberWithDashes_whenCreatePhoneNumber_thenNoExceptionIsThrown() {
        String phoneNumber = "234-235-5678";

        new PhoneNumber(phoneNumber);
    }

    @Test
    public void givenPhoneNumberWithSpaces_whenCreatePhoneNumber_thenNoExceptionIsThrown() {
        String phoneNumber = "234 235 5678";

        new PhoneNumber(phoneNumber);
    }

    @Test
    public void givenPhoneNumberWithDots_whenCreatePhoneNumber_thenNoExceptionIsThrown() {
        String phoneNumber = "234.235.5678";

        new PhoneNumber(phoneNumber);
    }

    @Test(expected = InvalidPhoneNumberException.class)
    public void givenPhoneNumberWithInvalidCentralOffice_whenCreatePhoneNumber_thenThrowsInvalidPhoneNumberException() {
        String phoneNumber = "314 159 2653";

        new PhoneNumber(phoneNumber);
    }

    @Test(expected = InvalidPhoneNumberException.class)
    public void givenPhoneNumberWithInvalidNumberingPlanArea_whenCreatePhoneNumber_thenThrowsInvalidPhoneNumberException
        () {
        String phoneNumber = "123 234 5678";

        new PhoneNumber(phoneNumber);
    }

    @Test(expected = InvalidPhoneNumberException.class)
    public void givenPhoneNumberWithAlphaNumericalCharacters_whenCreatePhoneNumber_thenThrowsInvalidPhoneNumberException
        () {
        String phoneNumber = "1b3 2z4 56a8";

        new PhoneNumber(phoneNumber);
    }

    @Test(expected = InvalidPhoneNumberException.class)
    public void givenPhoneNumberWithSpecialCharacters_whenCreatePhoneNumber_thenThrowsInvalidPhoneNumberException() {
        String phoneNumber = "1!3 2?4 56!8";

        new PhoneNumber(phoneNumber);
    }

    @Test(expected = InvalidPhoneNumberException.class)
    public void givenPhoneNumberTooLong_whenCreatePhoneNumber_thenThrowsInvalidPhoneNumberException() {
        String phoneNumber = "234 235 56784";

        new PhoneNumber(phoneNumber);
    }
}
