package ca.ulaval.glo4003.ultaxi.infrastructure.user;

import ca.ulaval.glo4003.ultaxi.domain.search.SearchResults;
import ca.ulaval.glo4003.ultaxi.domain.search.driver.DriverSearchQuery;
import ca.ulaval.glo4003.ultaxi.domain.user.User;
import ca.ulaval.glo4003.ultaxi.domain.user.UserRepository;
import ca.ulaval.glo4003.ultaxi.domain.user.driver.Driver;
import ca.ulaval.glo4003.ultaxi.domain.user.exception.NonExistentUserException;
import ca.ulaval.glo4003.ultaxi.domain.user.exception.UserAlreadyExistsException;
import ca.ulaval.glo4003.ultaxi.infrastructure.user.driver.DriverSearchQueryBuilderInMemory;
import ca.ulaval.glo4003.ultaxi.transfer.user.UserPersistenceDto;
import ca.ulaval.glo4003.ultaxi.transfer.user.driver.DriverSearchParameters;

import java.util.HashMap;
import java.util.Map;

public class UserRepositoryInMemory implements UserRepository {

    private final Map<String, UserPersistenceDto> users = new HashMap<>();

    @Override
    public UserPersistenceDto findByUsername(String username) {
        String searchedUsername = "";
        if (username != null) {
            searchedUsername = username;
        }
        return users.get(searchedUsername.trim().toLowerCase());
    }

    @Override
    public void save(UserPersistenceDto user) {
        String username = user.getUsername();
        if (users.containsKey(username)) {
            throw new UserAlreadyExistsException(
                String.format("User with userName %s already exists.", username)
            );
        }
        users.put(username, user);
    }

    @Override
    public void update(UserPersistenceDto user) {
        String username = user.getUsername();
        if (!users.containsKey(username)) {
            throw new NonExistentUserException(String.format("User with userName %s don't exist",
                                                             username));
        }
        users.put(username, user);
    }

    @Override
    public SearchResults<UserPersistenceDto> searchDrivers(DriverSearchParameters driverSearchParameters) {
        DriverSearchQuery query = new DriverSearchQueryBuilderInMemory(users)
            .withFirstName(driverSearchParameters.getFirstName())
            .withLastName(driverSearchParameters.getLastName())
            .withSocialInsuranceNumber(driverSearchParameters.getSocialInsuranceNumber())
            .build();

        return query.execute();
    }
}
