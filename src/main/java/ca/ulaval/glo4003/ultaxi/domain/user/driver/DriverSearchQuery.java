package ca.ulaval.glo4003.ultaxi.domain.user.driver;

public interface DriverSearchQuery extends SearchQuery<Driver> {

    SearchResults<Driver> execute();
}
