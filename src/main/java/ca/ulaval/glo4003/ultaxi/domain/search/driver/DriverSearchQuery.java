package ca.ulaval.glo4003.ultaxi.domain.search.driver;

import ca.ulaval.glo4003.ultaxi.domain.search.SearchQuery;
import ca.ulaval.glo4003.ultaxi.domain.search.SearchResults;
import ca.ulaval.glo4003.ultaxi.domain.user.driver.Driver;

public interface DriverSearchQuery extends SearchQuery<Driver> {

    SearchResults<Driver> execute();
}
