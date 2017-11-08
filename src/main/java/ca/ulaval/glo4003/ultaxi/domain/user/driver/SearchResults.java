package ca.ulaval.glo4003.ultaxi.domain.user.driver;

import ca.ulaval.glo4003.ultaxi.domain.user.exception.EmptySearchResultsException;

import java.util.Collections;
import java.util.List;

public class SearchResults<T> {

    private final List<T> resultsFound;

    public SearchResults(List<T> resultsFound) {
        if (resultsFound == null || resultsFound.isEmpty()) {
            throw new EmptySearchResultsException("No results found.");
        }

        this.resultsFound = resultsFound;
    }

    public List<T> getResultsList() {
        return Collections.unmodifiableList(resultsFound);
    }
}
