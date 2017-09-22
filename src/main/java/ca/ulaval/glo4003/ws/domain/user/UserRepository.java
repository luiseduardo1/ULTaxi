package ca.ulaval.glo4003.ws.domain.user;

import java.util.List;

public interface UserRepository {

    List<User> findAll();

    User findById(String id);

    void update(User user) throws UserNotFoundException;

    void save(User user);

    void remove(String id);
}
