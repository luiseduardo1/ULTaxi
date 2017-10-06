package ca.ulaval.glo4003.ws.infrastructure.user;

import ca.ulaval.glo4003.ws.domain.user.exception.InvalidUserNameException;
import ca.ulaval.glo4003.ws.domain.user.User;
import ca.ulaval.glo4003.ws.domain.user.exception.UserAlreadyExistsException;
import ca.ulaval.glo4003.ws.domain.user.UserRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class UserRepositoryInMemory implements UserRepository {

    private static final Pattern INVALID_NAME_PATTERN = Pattern.compile(".*@.*", Pattern.CASE_INSENSITIVE);
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
