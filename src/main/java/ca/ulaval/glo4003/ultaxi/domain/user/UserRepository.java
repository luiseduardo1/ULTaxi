package ca.ulaval.glo4003.ultaxi.domain.user;

public interface UserRepository {

    User findByName(String name);

    void save(User user);
}
