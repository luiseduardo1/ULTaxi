package ca.ulaval.glo4003.ultaxi.domain.user;

import ca.ulaval.glo4003.ultaxi.domain.search.SearchResults;
import ca.ulaval.glo4003.ultaxi.domain.user.driver.Driver;
import ca.ulaval.glo4003.ultaxi.transfer.user.UserPersistenceDto;
import ca.ulaval.glo4003.ultaxi.transfer.user.driver.DriverSearchParameters;

public interface UserRepository {

    UserPersistenceDto findByUsername(String username);

    void save(UserPersistenceDto user);

    void update(UserPersistenceDto user);

    SearchResults<UserPersistenceDto> searchDrivers(DriverSearchParameters driverSearchParameters);
}
