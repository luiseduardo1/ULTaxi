package ca.ulaval.glo4003.ultaxi.infrastructure.user.driver;

import ca.ulaval.glo4003.ultaxi.domain.search.SearchResults;
import ca.ulaval.glo4003.ultaxi.domain.search.driver.DriverSearchQuery;
import ca.ulaval.glo4003.ultaxi.domain.search.driver.DriverSearchQueryBuilder;
import ca.ulaval.glo4003.ultaxi.domain.user.Role;
import ca.ulaval.glo4003.ultaxi.domain.user.User;
import ca.ulaval.glo4003.ultaxi.domain.user.driver.Driver;

import java.util.HashSet;
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
    public DriverSearchQuery build() {
        return new DriverSearchQueryInMemory(users, predicates);
    }

    @Override
    public DriverSearchQueryBuilder withFirstName(String firstName) {
        return withNonNull(driver -> isSubsetOf(driver.getFirstName(), firstName), firstName);
    }

    @Override
    public DriverSearchQueryBuilder withLastName(String lastName) {
        return withNonNull(driver -> isSubsetOf(driver.getLastName(), lastName), lastName);
    }

    @Override
    public DriverSearchQueryBuilder withSocialInsuranceNumber(String socialInsuranceNumber) {
        return withNonNull(driver -> (driver.getSocialInsuranceNumber().getNumber()).equals(socialInsuranceNumber
                                                                                                .trim()),
                           socialInsuranceNumber);
    }

    private DriverSearchQueryBuilder withNonNull(Predicate<Driver> predicate, String value) {
        if (value != null) {
            predicates.add(predicate);
        }

        return this;
    }

    private boolean isSubsetOf(String value, String subset) {
        return value != null && value.toLowerCase().trim().contains(subset.toLowerCase().trim());
    }

    private class DriverSearchQueryInMemory implements DriverSearchQuery {

        private final Set<Predicate<Driver>> predicates;
        private final Map<String, User> users;

        private DriverSearchQueryInMemory(Map<String, User> users, Set<Predicate<Driver>> predicates) {
            this.users = users;
            this.predicates = predicates;
        }

        @Override
        public SearchResults<Driver> execute() {
            Stream<Driver> drivers = users
                .values()
                .stream()
                .filter(user -> Role.DRIVER == user.getRole())
                .map(user -> (Driver) user);
            return new SearchResults<>(
                predicates
                    .stream()
                    .reduce(drivers, Stream::filter, (x, y) -> y)
                    .collect(Collectors.toList())
            );
        }
    }
}
