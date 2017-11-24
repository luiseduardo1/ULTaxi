package ca.ulaval.glo4003.ultaxi.domain.search.exception;

public class EmptySearchResultsException extends RuntimeException {

    public EmptySearchResultsException(String message) {
        super(message);
    }
}
