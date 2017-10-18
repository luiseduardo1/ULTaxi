package ca.ulaval.glo4003.ultaxi.infrastructure.transportrequest;

import ca.ulaval.glo4003.ultaxi.domain.transportrequest.TransportRequest;
import ca.ulaval.glo4003.ultaxi.domain.transportrequest.TransportRequestSearchQueryBuilder;
import ca.ulaval.glo4003.ultaxi.domain.user.exception.EmptySearchResultsException;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TransportRequestSearchQueryBuilderInMemory implements TransportRequestSearchQueryBuilder {

    private final Map<String, TransportRequest> transportRequests;
    private final Set<Predicate<TransportRequest>> predicates = new HashSet<>();

    public TransportRequestSearchQueryBuilderInMemory(Map<String, TransportRequest> transportRequests) {
        this.transportRequests = transportRequests;
    }

    @Override
    public List<TransportRequest> findAll() {
        Stream<TransportRequest> transportRequests = this.transportRequests
                .values()
                .stream();

        return throwIfEmptySearchResults(
                predicates
                        .stream()
                        .reduce(transportRequests, Stream::filter, (x, y) -> y)
                        .collect(Collectors.toList())
        );
    }

    private List<TransportRequest> throwIfEmptySearchResults(List<TransportRequest> searchResults) {
        if (searchResults == null || searchResults.isEmpty()) {
            throw new EmptySearchResultsException("No search results.");
        }

        return searchResults;
    }

    @Override
    public TransportRequestSearchQueryBuilder withVehicleType(String vehicleType) {
        return withNonNull(transportRequest -> isSubsetOf(transportRequest.getVehicleType(), vehicleType), vehicleType);
    }

    private TransportRequestSearchQueryBuilder withNonNull(Predicate<TransportRequest> predicate, String value) {
        if (value != null) {
            predicates.add(predicate);
        }

        return this;
    }

    private boolean isSubsetOf(String value, String subset) {
        return value.toLowerCase().contains(subset.toLowerCase().trim());
    }

}
