package ca.ulaval.glo4003.ultaxi.domain.user.exception;

public class InvalidEmailAddressException extends RuntimeException {

    public InvalidEmailAddressException(String emailAddress) {
        super(emailAddress);
    }
}
