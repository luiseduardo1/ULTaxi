package ca.ulaval.glo4003.ultaxi.domain.user;

import ca.ulaval.glo4003.ultaxi.domain.user.driver.DriverSearchQueryBuilder;

public interface UserRepository {

    User findByUsername(String username);

    void save(User user);

    DriverSearchQueryBuilder searchDrivers();

    void update(User user);
}
