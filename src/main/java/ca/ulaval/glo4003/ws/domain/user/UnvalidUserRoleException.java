package ca.ulaval.glo4003.ws.domain.user;

public class UnvalidUserRoleException extends RuntimeException {
    public UnvalidUserRoleException(String name) {
        super(name);
    }
}
