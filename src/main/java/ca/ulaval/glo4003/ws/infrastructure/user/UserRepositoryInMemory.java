package ca.ulaval.glo4003.ws.infrastructure.user;

import ca.ulaval.glo4003.ws.domain.user.User;
import ca.ulaval.glo4003.ws.domain.user.UserRepository;
import ca.ulaval.glo4003.ws.domain.user.exception.UserAlreadyExistsException;

import java.util.HashMap;
import java.util.Map;

public class UserRepositoryInMemory implements UserRepository {

    private Map<String, User> users = new HashMap<>();

    @Override
    public User findByName(String name) {
        String formattedName = name.toLowerCase().trim();
        return users.get(formattedName);
    }

    @Override
    public void save(User user) {
        String name = user.getUserName().toLowerCase().trim();
        if (users.containsKey(name)) {
            throw new UserAlreadyExistsException(
                    String.format("User with userName %s already exists.", user.getUserName())
            );
        }
        users.put(name, user);
    }
}
