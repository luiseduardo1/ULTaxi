package ca.ulaval.glo4003.ultaxi.domain.user;

public interface UserRepository {

    User findByUserName(String name);

    void save(User user);
}
