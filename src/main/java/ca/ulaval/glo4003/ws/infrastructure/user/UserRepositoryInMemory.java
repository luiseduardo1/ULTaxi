package ca.ulaval.glo4003.ws.infrastructure.user;

import ca.ulaval.glo4003.ws.domain.user.InvalidUserNameException;
import ca.ulaval.glo4003.ws.domain.user.User;
import ca.ulaval.glo4003.ws.domain.user.UserAlreadyExistsException;
import ca.ulaval.glo4003.ws.domain.user.UserRepository;

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
        if (!user.isValid()) {
            throw new InvalidUserNameException(
                String.format("%s is not a valid name.", user.getName())
            );
        }
        String name = user.getName().toLowerCase().trim();
        if (users.containsKey(name)) {
            throw new UserAlreadyExistsException(
                String.format("User with name %s already exists.", user.getName())
            );
        }
        users.put(name, user);
    }
}
