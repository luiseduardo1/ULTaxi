package ca.ulaval.glo4003.ultaxi.infrastructure.user;

import ca.ulaval.glo4003.ultaxi.domain.user.User;
import ca.ulaval.glo4003.ultaxi.domain.user.UserRepository;
import ca.ulaval.glo4003.ultaxi.domain.user.driver.DriverSearchQuery;
import ca.ulaval.glo4003.ultaxi.domain.user.driver.DriverSearchQueryBuilder;
import ca.ulaval.glo4003.ultaxi.domain.user.exception.NonExistentUserException;
import ca.ulaval.glo4003.ultaxi.domain.user.exception.UserAlreadyExistsException;
import ca.ulaval.glo4003.ultaxi.infrastructure.user.driver.DriverSearchQueryBuilderInMemory;
import ca.ulaval.glo4003.ultaxi.transfer.user.driver.DriverSearchParameters;

import java.util.HashMap;
import java.util.Map;

public class UserRepositoryInMemory implements UserRepository {

    private final Map<String, User> users = new HashMap<>();

    @Override
    public User findByUsername(String username) {
        String searchedUsername = "";
        if (username != null) {
            searchedUsername = username;
        }
        return users.get(searchedUsername.trim().toLowerCase());
    }

    @Override
    public void save(User user) {
        String name = user.getUsername();
        if (users.containsKey(name)) {
            throw new UserAlreadyExistsException(
                String.format("User with userName %s already exists.", name)
            );
        }
        users.put(name, user);
    }

    @Override
    public void update(User user) {
        String name = user.getUsername();
        if (!users.containsKey(name)) {
            throw new NonExistentUserException(String.format("User with userName %s don't exist",
                                                             name));
        }
        users.put(name, user);
    }

    @Override
    public DriverSearchQuery searchDrivers(DriverSearchParameters driverSearchParameters) {
        return new DriverSearchQueryBuilderInMemory(users)
            .withFirstName(driverSearchParameters.getFirstName())
            .withLastName(driverSearchParameters.getLastName())
            .withSocialInsuranceNumber(driverSearchParameters.getSocialInsuranceNumber())
            .build();
    }
}
