package ca.ulaval.glo4003.ws.infrastructure.user;

import ca.ulaval.glo4003.ws.domain.user.exception.InvalidUserNameException;
import ca.ulaval.glo4003.ws.domain.user.User;
import ca.ulaval.glo4003.ws.domain.user.exception.UserAlreadyExistsException;
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
        if (isInvalidName(user.getUserName())) {
            throw new InvalidUserNameException(
                String.format("%s is not a valid userName.", user.getUserName())
            );
        }
        if (isUserPresent(user)) {
            throw new UserAlreadyExistsException(
                String.format("User with userName %s already exists.", user.getUserName())
            );
        }
        users.put(user.getUserName(), user);
    }

    private boolean isInvalidName(String name) {
        return INVALID_NAME_PATTERN
            .matcher(name)
            .find();
    }

    private boolean isUserPresent(User user) {
        String name = user.getUserName().toLowerCase().trim();
        return users
            .values()
            .stream()
            .anyMatch(
                x -> name.equals(x.getUserName().toLowerCase().trim())
            );
    }
}
