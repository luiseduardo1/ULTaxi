package ca.ulaval.glo4003.ultaxi.domain.user.client;

import ca.ulaval.glo4003.ultaxi.domain.user.Role;
import ca.ulaval.glo4003.ultaxi.domain.user.User;

public class Client extends User {

    public Client() {
        this.setRole(Role.CLIENT);
    }

}
