package ca.ulaval.glo4003.ultaxi.infrastructure.user;

import ca.ulaval.glo4003.ultaxi.domain.user.Role;
import ca.ulaval.glo4003.ultaxi.domain.user.User;
import ca.ulaval.glo4003.ultaxi.domain.user.administrator.Administrator;
import ca.ulaval.glo4003.ultaxi.domain.user.driver.Driver;
import ca.ulaval.glo4003.ultaxi.utils.hashing.HashingStrategy;
import jersey.repackaged.com.google.common.collect.Lists;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class UserDevDataFactory {

    private final Map<Role, Function<HashingStrategy, User>> userTypesByRoles = new HashMap<>();
    private final Map<Role, Function<HashingStrategy, User>> secondUserTypesByRoles = new HashMap<>();

    public UserDevDataFactory() {
        userTypesByRoles.put(Role.DRIVER, this::createDriver);
        userTypesByRoles.put(Role.CLIENT, this::createClient);
        userTypesByRoles.put(Role.ADMINISTRATOR, this::createAdministrator);
        secondUserTypesByRoles.put(Role.CLIENT, this::createSecondClient);
    }

    public List<User> createMockData(HashingStrategy hashingStrategy) throws Exception {
        List<User> users = Lists.newArrayList();
        for (Role role : Role.values()) {
            if (role != Role.ANONYMOUS) {
                users.add(
                        createGenericRoleUser(role, hashingStrategy)
                );
            }
        }

        users.add(createSecondGenericRoleUser(Role.CLIENT, hashingStrategy));

        return users;
    }

    private User createGenericRoleUser(Role role, HashingStrategy hashingStrategy) throws Exception {
        return userTypesByRoles.get(role).apply(hashingStrategy);
    }

    private User createSecondGenericRoleUser(Role role, HashingStrategy hashingStrategy) throws Exception {
        return secondUserTypesByRoles.get(role).apply(hashingStrategy);
    }

    private User createDriver(HashingStrategy hashingStrategy) {
        Driver driver = (Driver) updateBaseAttributes(new Driver(), Role.DRIVER, hashingStrategy);
        String lowercaseRole = Role.DRIVER.name().toLowerCase();
        driver.setPhoneNumber("2342355678");
        driver.setSocialInsuranceNumber("972487086");
        driver.setLastName(lowercaseRole + "LastName");
        driver.setName(lowercaseRole + "Name");

        return driver;
    }

    private User createClient(HashingStrategy hashingStrategy) {
        return updateBaseAttributes(new User(), Role.CLIENT, hashingStrategy);
    }

    private User createSecondClient(HashingStrategy hashingStrategy) {
        return updateSecondBaseAttributes(new User(), Role.CLIENT, hashingStrategy);
    }

    private User createAdministrator(HashingStrategy hashingStrategy) {
        return updateBaseAttributes(new Administrator(), Role.ADMINISTRATOR, hashingStrategy);
    }

    private User updateBaseAttributes(User user, Role role, HashingStrategy hashingStrategy) {
        String lowercaseRole = role.name().toLowerCase();
        user.setRole(role);
        user.setUsername(lowercaseRole + "Username");
        user.setPassword(lowercaseRole + "Password", hashingStrategy);
        user.setEmailAddress(lowercaseRole + "@ultaxi.ca");
        return user;
    }

    private User updateSecondBaseAttributes(User user, Role role, HashingStrategy hashingStrategy) {
        String lowercaseRole = role.name().toLowerCase();
        user.setRole(role);
        user.setUsername(lowercaseRole + "SecondUsername");
        user.setPassword(lowercaseRole + "SecondPassword", hashingStrategy);
        user.setEmailAddress(lowercaseRole + "second@ultaxi.ca");
        return user;
    }
}
