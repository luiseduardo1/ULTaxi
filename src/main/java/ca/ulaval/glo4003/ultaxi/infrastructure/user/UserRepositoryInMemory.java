package ca.ulaval.glo4003.ultaxi.infrastructure.user;

import ca.ulaval.glo4003.ultaxi.domain.user.User;
import ca.ulaval.glo4003.ultaxi.domain.user.UserRepository;
import ca.ulaval.glo4003.ultaxi.domain.user.exception.UserAlreadyExistsException;

import java.util.HashMap;
import java.util.Map;

public class UserRepositoryInMemory implements UserRepository {

    private Map<String, User> users = new HashMap<>();

    @Override
    public User findByUserName(String name) {
        return users.get(name);
    }

    @Override
    public void save(User user) {
        String name = user.getUsername();
        if (users.containsKey(name)) {
            throw new UserAlreadyExistsException(
                String.format("User with userName %s already exists.", user.getUsername())
            );
        }
        users.put(name, user);
    }
}
