package ca.ulaval.glo4003.ultaxi.domain.search.driver;

import ca.ulaval.glo4003.ultaxi.domain.search.SearchQuery;
import ca.ulaval.glo4003.ultaxi.domain.search.SearchResults;
import ca.ulaval.glo4003.ultaxi.domain.user.driver.Driver;
import ca.ulaval.glo4003.ultaxi.transfer.user.UserPersistenceDto;

public interface DriverSearchQuery extends SearchQuery<Driver> {

    SearchResults<UserPersistenceDto> execute();
}
