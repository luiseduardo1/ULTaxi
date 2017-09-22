package ca.ulaval.glo4003.ws.infrastructure.user;

import ca.ulaval.glo4003.ws.domain.user.User;
import ca.ulaval.glo4003.ws.domain.user.UserNotFoundException;
import ca.ulaval.glo4003.ws.domain.user.UserRepository;
import jersey.repackaged.com.google.common.collect.Lists;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserRepositoryInMemory implements UserRepository {

    private Map<String, User> contacts = new HashMap<>();

    @Override
    public List<User> findAll() {
        return Lists.newArrayList(contacts.values());
    }

    @Override
    public User findById(String id) {
        return contacts.get(id);
    }

    @Override
    public void update(User user) throws UserNotFoundException {
        User foundUser = contacts.get(user.getId());
        if (foundUser != null) {
            contacts.put(user.getId(), user);
        } else {
            throw new UserNotFoundException("User not found, cannot be updated");
        }
    }

    @Override
    public void save(User user) {
        contacts.put(user.getId(), user);
    }

    @Override
    public void remove(String id) {
        contacts.remove(id);
    }
}
