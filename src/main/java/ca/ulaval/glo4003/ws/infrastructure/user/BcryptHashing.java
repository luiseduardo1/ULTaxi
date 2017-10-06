package ca.ulaval.glo4003.ws.infrastructure.user;

import ca.ulaval.glo4003.ws.domain.user.HashingStrategy;
import org.mindrot.jbcrypt.BCrypt;

public class BcryptHashing implements HashingStrategy {
    @Override
    public String hash(String password) {
        String hashedPassword = null;
        if (password != null) {
            String salt = BCrypt.gensalt();
            hashedPassword = BCrypt.hashpw(password, salt);
        }

        return hashedPassword;
    }

    @Override
    public boolean areEquals(String plainPassword, String hashedPassword) {
        return plainPassword != null
            && hashedPassword != null
            && BCrypt.checkpw(plainPassword, hashedPassword);
    }
}
