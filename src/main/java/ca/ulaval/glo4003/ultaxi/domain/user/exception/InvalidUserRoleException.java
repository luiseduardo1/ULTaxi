package ca.ulaval.glo4003.ultaxi.domain.user.exception;

public class InvalidUserRoleException extends RuntimeException {

    public InvalidUserRoleException(String name) {
        super(name);
    }
}
