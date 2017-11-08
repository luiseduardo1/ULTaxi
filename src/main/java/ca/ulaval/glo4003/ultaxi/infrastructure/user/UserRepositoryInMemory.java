package ca.ulaval.glo4003.ultaxi.infrastructure.user;

import ca.ulaval.glo4003.ultaxi.domain.user.User;
import ca.ulaval.glo4003.ultaxi.domain.user.UserRepository;
import ca.ulaval.glo4003.ultaxi.domain.user.driver.DriverSearchQueryBuilder;
import ca.ulaval.glo4003.ultaxi.domain.user.exception.NonExistentUserException;
import ca.ulaval.glo4003.ultaxi.domain.user.exception.UserAlreadyExistsException;
import ca.ulaval.glo4003.ultaxi.infrastructure.user.driver.DriverSearchQueryBuilderInMemory;

import java.util.HashMap;
import java.util.Map;

public class UserRepositoryInMemory implements UserRepository {

    private final Map<String, User> users = new HashMap<>();

    @Override
    public User findByUsername(String username) {
        String searchedUsername = "";
        if (username != null) {
            searchedUsername = username;
        }
        return users.get(searchedUsername.trim().toLowerCase());
    }

    @Override
    public void save(User user) {
        String username = user.getUsername();
        if (users.containsKey(username)) {
            throw new UserAlreadyExistsException(
                String.format("User with userName %s already exists.", username)
            );
        }
        users.put(username, user);
    }

    @Override
    public void update(User user) {
        String username = user.getUsername();
        if (!users.containsKey(username)) {
            throw new NonExistentUserException(String.format("User with userName %s don't exist",
                                                             username));
        }
        users.put(username, user);
    }

    @Override
    public DriverSearchQueryBuilder searchDrivers() {
        return new DriverSearchQueryBuilderInMemory(users);
    }
}
