package ca.ulaval.glo4003.ultaxi.domain.user.client;

import ca.ulaval.glo4003.ultaxi.domain.user.Role;
import ca.ulaval.glo4003.ultaxi.domain.user.User;
import ca.ulaval.glo4003.ultaxi.utils.hashing.HashingStrategy;

public class Client extends User {

    public Client(String username, String password, String phoneNumber, String emailAddress,
        HashingStrategy hashingStrategy) {
        super(username, password, phoneNumber, emailAddress, hashingStrategy);
    }

    @Override
    public Role getRole() {
        return Role.CLIENT;
    }
}
