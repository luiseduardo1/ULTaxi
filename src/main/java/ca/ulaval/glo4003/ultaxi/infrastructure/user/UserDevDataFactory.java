package ca.ulaval.glo4003.ultaxi.infrastructure.user;

import ca.ulaval.glo4003.ultaxi.domain.user.Role;
import ca.ulaval.glo4003.ultaxi.domain.user.User;
import ca.ulaval.glo4003.ultaxi.domain.user.administrator.Administrator;
import ca.ulaval.glo4003.ultaxi.domain.user.client.Client;
import ca.ulaval.glo4003.ultaxi.domain.user.driver.Driver;
import ca.ulaval.glo4003.ultaxi.utils.hashing.HashingStrategy;
import jersey.repackaged.com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class UserDevDataFactory {

    private static final int CLIENT_QUANTITY = 2;
    private static final int ADMINISTRATOR_QUANTITY = 1;
    private static final int DRIVER_QUANTITY = 1;
    private static final String PHONE_NUMBER = "2342355678";
    private static final String SOCIAL_INSURANCE_NUMBER = "972487086";
    private static final String USERNAME_SUFFIX = "Username";
    private static final String PASSWORD_SUFFIX = "Password";
    private static final String EMAIL_SUFFIX = "@ultaxi.ca";
    private static final String FIRST_NAME_SUFFIX = "FirstName";
    private static final String LAST_NAME_SUFFIX = "LastName";

    private final Map<Role, Function<HashingStrategy, List<User>>> userTypesByRoles = new HashMap<>();

    public UserDevDataFactory() {
        userTypesByRoles.put(Role.DRIVER, this::createDrivers);
        userTypesByRoles.put(Role.CLIENT, this::createClients);
        userTypesByRoles.put(Role.ADMINISTRATOR, this::createAdministrators);
    }

    public List<User> createMockData(HashingStrategy hashingStrategy) throws Exception {
        List<User> users = Lists.newArrayList();
        for (Role role : Role.values()) {
            users.addAll(
                createGenericRoleUsers(role, hashingStrategy)
            );
        }
        return users;
    }

    private List<User> createGenericRoleUsers(Role role, HashingStrategy hashingStrategy) throws Exception {
        return userTypesByRoles.get(role).apply(hashingStrategy);
    }

    private List<User> createAdministrators(HashingStrategy hashingStrategy) {
        List<User> users = new ArrayList<>();
        for (int i = 0; i <= ADMINISTRATOR_QUANTITY; i++) {
            users.add(createAdministrator(hashingStrategy, i));
        }
        return users;
    }

    private List<User> createClients(HashingStrategy hashingStrategy) {
        List<User> users = new ArrayList<>();
        for (int i = 0; i <= CLIENT_QUANTITY; i++) {
            users.add(createClient(hashingStrategy, i));
        }
        return users;
    }

    private List<User> createDrivers(HashingStrategy hashingStrategy) {
        List<User> drivers = new ArrayList<>();
        for (int i = 0; i <= DRIVER_QUANTITY; i++) {
            drivers.add(createDriver(hashingStrategy, i));
        }
        return drivers;
    }

    private User createAdministrator(HashingStrategy hashingStrategy, int administratorNumber) {
        String administratorPrefix = generateUserPrefixString(Role.ADMINISTRATOR, administratorNumber);
        Administrator administrator = new Administrator(
            administratorPrefix + USERNAME_SUFFIX,
            administratorPrefix + PASSWORD_SUFFIX,
            PHONE_NUMBER,
            administratorPrefix + EMAIL_SUFFIX,
            hashingStrategy
        );
        return administrator;
    }

    private User createClient(HashingStrategy hashingStrategy, int clientNumber) {
        String clientPrefix = generateUserPrefixString(Role.CLIENT, clientNumber);
        Client client = new Client(
            clientPrefix + USERNAME_SUFFIX,
            clientPrefix + PASSWORD_SUFFIX,
            PHONE_NUMBER,
            clientPrefix + EMAIL_SUFFIX,
            hashingStrategy
        );
        return client;
    }

    private User createDriver(HashingStrategy hashingStrategy, int driverNumber) {
        String driverPrefix = generateUserPrefixString(Role.DRIVER, driverNumber);
        Driver driver = new Driver(
            driverPrefix + USERNAME_SUFFIX,
            driverPrefix + PASSWORD_SUFFIX,
            PHONE_NUMBER,
            driverPrefix + EMAIL_SUFFIX,
            hashingStrategy,
            driverPrefix + FIRST_NAME_SUFFIX,
            driverPrefix + LAST_NAME_SUFFIX,
            SOCIAL_INSURANCE_NUMBER
        );
        return driver;
    }

    private String generateUserPrefixString(Role role, int index) {
        return role.name().toLowerCase() + String.valueOf(index);
    }
}
