package ca.ulaval.glo4003.ws.infrastructure.user;

import ca.ulaval.glo4003.ws.domain.user.InvalidCredentialsException;
import ca.ulaval.glo4003.ws.domain.user.InvalidUserNameException;
import ca.ulaval.glo4003.ws.domain.user.Role;
import ca.ulaval.glo4003.ws.domain.user.User;
import ca.ulaval.glo4003.ws.domain.user.UserAlreadyExistsException;
import ca.ulaval.glo4003.ws.domain.user.UserRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class UserRepositoryInMemory implements UserRepository {

    private Map<String, User> users = new HashMap<>();
    private static final Pattern INVALID_NAME_PATTERN = Pattern.compile(".*@.*", Pattern.CASE_INSENSITIVE);

    @Override
    public User findByName(String id) {
        return users.get(id);
    }

    @Override
    public void save(User user) {
        if (isInvalidName(user.getName())) {
            throw new InvalidUserNameException(
                String.format("%s is not a valid name.", user.getName())
            );
        }
        if (isUserPresent(user)) {
            throw new UserAlreadyExistsException(
                String.format("User with name %s already exists.", user.getName())
            );
        }
        users.put(user.getName(), user);
    }

    //TODO : put in domain
    @Override
    public boolean authenticate(User user) {
        if (isUserPresent(user)) {
            User userToAuthenticate = findByName(user.getName());
            String password = userToAuthenticate.getPassword();
            if (user.getPassword().equals(password)) {
                return true;
            } else {
                throw new InvalidCredentialsException(
                    String.format("User, %s, credentials are invalid", user.getName())
                );
            }
        }
        return false;
    }

    public Role getUserRole(String userName) {
        return findByName(userName).getRole();
    }

    private boolean isInvalidName(String name) {
        return INVALID_NAME_PATTERN
            .matcher(name)
            .find();
    }

    private boolean isUserPresent(User user) {
        String name = user.getName().toLowerCase().trim();
        return users
            .values()
            .stream()
            .anyMatch(
                x -> name.equals(x.getName().toLowerCase().trim())
            );
    }
}
