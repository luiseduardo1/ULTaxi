package ca.ulaval.glo4003.ultaxi.domain.user;

import ca.ulaval.glo4003.ultaxi.domain.user.driver.DriverSearchQuery;
import ca.ulaval.glo4003.ultaxi.transfer.user.driver.DriverSearchParameters;

public interface UserRepository {

    User findByUsername(String username);

    void save(User user);

    void update(User user);

    DriverSearchQuery searchDrivers(DriverSearchParameters driverSearchParameters);
}
