package ca.ulaval.glo4003.ws.infrastructure.user;

import ca.ulaval.glo4003.ws.domain.user.User;
import ca.ulaval.glo4003.ws.domain.user.UserAlreadyExistsException;
import ca.ulaval.glo4003.ws.domain.user.UserRepository;

import java.util.HashMap;
import java.util.Map;

public class UserRepositoryInMemory implements UserRepository {

    private Map<String, User> users = new HashMap<>();

    @Override
    public User findByName(String id) {
        return users.get(id);
    }

    @Override
    public void save(User user) {
        if (isUserPresent(user)) {
            throw new UserAlreadyExistsException(
                String.format("User with name %s already exists.", user.getName())
            );
        }
        users.put(user.getName(), user);
    }

    private boolean isUserPresent(User user) {
        return users
                .values()
                .stream()
                .anyMatch(x -> user.getName().equals(x.getName()));

    }
}
