package ca.ulaval.glo4003.ultaxi.infrastructure.user.driver;

import ca.ulaval.glo4003.ultaxi.domain.search.SearchResults;
import ca.ulaval.glo4003.ultaxi.domain.search.driver.DriverSearchQuery;
import ca.ulaval.glo4003.ultaxi.domain.search.driver.DriverSearchQueryBuilder;
import ca.ulaval.glo4003.ultaxi.domain.user.Role;
import ca.ulaval.glo4003.ultaxi.transfer.user.UserPersistenceDto;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DriverSearchQueryBuilderInMemory implements DriverSearchQueryBuilder {

    private final Map<String, UserPersistenceDto> users;
    private final Set<Predicate<UserPersistenceDto>> predicates = new HashSet<>();

    public DriverSearchQueryBuilderInMemory(Map<String, UserPersistenceDto> users) {
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
        return withNonNull(
            driver -> (driver.getSocialInsuranceNumber().getNumber()).equals(socialInsuranceNumber.trim()),
            socialInsuranceNumber
        );
    }

    private DriverSearchQueryBuilder withNonNull(Predicate<UserPersistenceDto> predicate, String value) {
        if (value != null) {
            predicates.add(predicate);
        }

        return this;
    }

    private boolean isSubsetOf(String value, String subset) {
        return value != null && value.toLowerCase().trim().contains(subset.toLowerCase().trim());
    }

    private class DriverSearchQueryInMemory implements DriverSearchQuery {

        private final Set<Predicate<UserPersistenceDto>> predicates;
        private final Map<String, UserPersistenceDto> users;

        private DriverSearchQueryInMemory(Map<String, UserPersistenceDto> users, Set<Predicate<UserPersistenceDto>> predicates) {
            this.users = users;
            this.predicates = predicates;
        }

        @Override
        public SearchResults<UserPersistenceDto> execute() {
            Stream<UserPersistenceDto> drivers = users
                .values()
                .stream()
                .filter(user -> Role.DRIVER == user.getRole());
            return new SearchResults<>(
                predicates
                    .stream()
                    .reduce(drivers, Stream::filter, (x, y) -> y)
                    .collect(Collectors.toList())
            );
        }
    }
}
