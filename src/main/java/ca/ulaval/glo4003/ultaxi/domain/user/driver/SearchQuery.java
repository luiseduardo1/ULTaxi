package ca.ulaval.glo4003.ultaxi.domain.user.driver;

public interface SearchQuery<T> {

    SearchResults<T> execute();
}
