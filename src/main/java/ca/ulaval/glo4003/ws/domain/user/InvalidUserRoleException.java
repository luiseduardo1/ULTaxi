package ca.ulaval.glo4003.ws.domain.user;

public class InvalidUserRoleException extends RuntimeException {

    public InvalidUserRoleException(String name) {
        super(name);
    }
}
