package ca.ulaval.glo4003.ultaxi.domain.user.administrator;

import ca.ulaval.glo4003.ultaxi.domain.user.PhoneNumber;
import ca.ulaval.glo4003.ultaxi.domain.user.Role;
import ca.ulaval.glo4003.ultaxi.domain.user.User;
import ca.ulaval.glo4003.ultaxi.utils.hashing.HashingStrategy;

public class Administrator extends User {

    public Administrator(String username, String password, PhoneNumber phoneNumber, String emailAddress,
        HashingStrategy hashingStrategy) {
        super(username, password, phoneNumber, emailAddress, hashingStrategy);
    }

    @Override
    public Role getRole() {
        return Role.ADMINISTRATOR;
    }
}
