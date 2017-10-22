package ca.ulaval.glo4003.ultaxi.infrastructure.user.driver;

import ca.ulaval.glo4003.ultaxi.domain.user.Role;
import ca.ulaval.glo4003.ultaxi.domain.user.User;
import ca.ulaval.glo4003.ultaxi.domain.user.driver.Driver;
import ca.ulaval.glo4003.ultaxi.domain.user.driver.DriverSearchQueryBuilder;
import ca.ulaval.glo4003.ultaxi.domain.user.exception.EmptySearchResultsException;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DriverSearchQueryBuilderInMemory implements DriverSearchQueryBuilder {

    private final Map<String, User> users;
    private final Set<Predicate<Driver>> predicates = new HashSet<>();

    public DriverSearchQueryBuilderInMemory(Map<String, User> users) {
        this.users = users;
    }

    @Override
    public List<Driver> findAll() {
        Stream<Driver> drivers = users
                .values()
                .stream()
                .filter(user -> user.getRole() == Role.Driver)
                .map(user -> (Driver) user);
        return throwIfEmptySearchResults(
                predicates
                        .stream()
                        .reduce(drivers, Stream::filter, (x, y) -> y)
                        .collect(Collectors.toList())
        );
    }

    private List<Driver> throwIfEmptySearchResults(List<Driver> searchResults) {
        if (searchResults == null || searchResults.isEmpty()) {
            throw new EmptySearchResultsException("No search results.");
        }

        return searchResults;
    }

    @Override
    public DriverSearchQueryBuilder withFirstName(String firstName) {
        return withNonNull(driver -> isSubsetOf(driver.getName(), firstName), firstName);
    }

    @Override
    public DriverSearchQueryBuilder withLastName(String lastName) {
        return withNonNull(driver -> isSubsetOf(driver.getLastName(), lastName), lastName);
    }

    @Override
    public DriverSearchQueryBuilder withSocialInsuranceNumber(String socialInsuranceNumber) {
        return withNonNull(driver -> driver.getSocialInsuranceNumber().equals(socialInsuranceNumber.trim()),
                           socialInsuranceNumber);
    }

    private DriverSearchQueryBuilder withNonNull(Predicate<Driver> predicate, String value) {
        if (value != null) {
            predicates.add(predicate);
        }

        return this;
    }

    private boolean isSubsetOf(String value, String subset) {
        return value.contains(subset.toLowerCase().trim());
    }
}
