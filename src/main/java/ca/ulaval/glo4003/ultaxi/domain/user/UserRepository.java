package ca.ulaval.glo4003.ultaxi.domain.user;

import ca.ulaval.glo4003.ultaxi.domain.user.driver.Driver;
import ca.ulaval.glo4003.ultaxi.domain.search.SearchResults;
import ca.ulaval.glo4003.ultaxi.transfer.user.driver.DriverSearchParameters;

public interface UserRepository {

    User findByUsername(String username);

    void save(User user);

    void update(User user);

    SearchResults<Driver> searchDrivers(DriverSearchParameters driverSearchParameters);
}
