package ca.ulaval.glo4003.ultaxi.utils.hashing;

import org.mindrot.jbcrypt.BCrypt;

public class BcryptHashing implements HashingStrategy {

    private final String salt = BCrypt.gensalt();

    @Override
    public String hash(String value) {
        return hashWithSalt(value, this.salt);
    }

    @Override
    public String hashWithRandomSalt(String value) {
        return hashWithSalt(value, BCrypt.gensalt());
    }

    @Override
    public boolean areEquals(String plainValue, String hashedValue) {
        return plainValue != null
            && hashedValue != null
            && BCrypt.checkpw(plainValue, hashedValue);
    }

    private String hashWithSalt(String value, String salt) {
        String hashedValue = null;
        if (value != null) {
            hashedValue = BCrypt.hashpw(value, salt);
        }

        return hashedValue;
    }
}
