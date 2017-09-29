package ca.ulaval.glo4003.ws.domain.user;

public interface UserRepository {

    User findByName(String name);

    void save(User user);

    boolean authenticate(User user);
}
