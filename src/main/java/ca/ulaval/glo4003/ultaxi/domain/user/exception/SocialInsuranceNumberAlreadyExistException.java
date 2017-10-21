package ca.ulaval.glo4003.ultaxi.domain.user.exception;

public class SocialInsuranceNumberAlreadyExistException extends RuntimeException {

    public SocialInsuranceNumberAlreadyExistException(String message) {
        super(message);
    }
}
