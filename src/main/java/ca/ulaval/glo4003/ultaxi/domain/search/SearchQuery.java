package ca.ulaval.glo4003.ultaxi.domain.search;

public interface SearchQuery<T> {

    SearchResults<T> execute();
}
