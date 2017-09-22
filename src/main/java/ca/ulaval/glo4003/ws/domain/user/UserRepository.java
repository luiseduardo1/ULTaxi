package ca.ulaval.glo4003.ws.domain.user;

public interface UserRepository {

    User findById(String id);

    void save(User user);
}
