package ca.ulaval.glo4003.ultaxi.domain.user.client;

import ca.ulaval.glo4003.ultaxi.domain.user.Role;
import ca.ulaval.glo4003.ultaxi.domain.user.User;
import ca.ulaval.glo4003.ultaxi.domain.user.exception.InvalidPhoneNumberException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Client extends User{

    public Client() {
        this.setRole(Role.CLIENT);
    }

}
