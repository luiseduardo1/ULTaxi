package ca.ulaval.glo4003.ultaxi.infrastructure.user;

import ca.ulaval.glo4003.ultaxi.domain.user.HashingStrategy;
import org.mindrot.jbcrypt.BCrypt;

public class BcryptHashing implements HashingStrategy {

    @Override
    public String hash(String value) {
        String hashedValue = null;
        if (value != null) {
            String salt = BCrypt.gensalt();
            hashedValue = BCrypt.hashpw(value, salt);
        }

        return hashedValue;
    }

    @Override
    public boolean areEquals(String plainValue, String hashedValue) {
        return plainValue != null
            && hashedValue != null
            && BCrypt.checkpw(plainValue, hashedValue);
    }
}
