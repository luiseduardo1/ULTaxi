package ca.ulaval.glo4003.ultaxi.infrastructure.user;

import ca.ulaval.glo4003.ultaxi.domain.user.PhoneNumber;
import ca.ulaval.glo4003.ultaxi.domain.user.Role;
import ca.ulaval.glo4003.ultaxi.domain.user.User;
import ca.ulaval.glo4003.ultaxi.domain.user.administrator.Administrator;
import ca.ulaval.glo4003.ultaxi.domain.user.driver.Driver;
import ca.ulaval.glo4003.ultaxi.utils.hashing.HashingStrategy;
import jersey.repackaged.com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class UserDevDataFactory {

    private final Map<Role, Function<HashingStrategy, List<User>>> userTypesByRoles = new HashMap<>();
    private static final int CLIENT_QUANTITY = 2;
    private static final int ADMINISTRATOR_QUANTITY = 1;
    private static final int DRIVER_QUANTITY = 1;

    public UserDevDataFactory() {
        userTypesByRoles.put(Role.DRIVER, this::createDrivers);
        userTypesByRoles.put(Role.CLIENT, this::createClients);
        userTypesByRoles.put(Role.ADMINISTRATOR, this::createAdministrators);
    }

    public List<User> createMockData(HashingStrategy hashingStrategy) throws Exception {
        List<User> users = Lists.newArrayList();
        for (Role role : Role.values()) {
            if (role != Role.ANONYMOUS) {
                users.addAll(
                        createGenericRoleUsers(role, hashingStrategy)
                );
            }
        }
        return users;
    }

    private List<User> createGenericRoleUsers(Role role, HashingStrategy hashingStrategy) throws Exception {
        return userTypesByRoles.get(role).apply(hashingStrategy);
    }

    private List<User> createDrivers(HashingStrategy hashingStrategy) {
        List<User> drivers = new ArrayList<>();
        for (int i = 0; i <= DRIVER_QUANTITY; i++) {
            drivers.add(createDriver(hashingStrategy, i));
        }
        return drivers;
    }

    private User createDriver(HashingStrategy hashingStrategy, int driverNumber) {
        Driver driver = (Driver) updateBaseAttributes(new Driver(), Role.DRIVER,
                hashingStrategy, String.valueOf(driverNumber));
        String lowercaseRole = Role.DRIVER.name().toLowerCase();
        PhoneNumber phoneNumber = new PhoneNumber("2342355678");
        driver.setPhoneNumber(phoneNumber);
        driver.setSocialInsuranceNumber("972487086");
        driver.setLastName(lowercaseRole + "LastName");
        driver.setName(lowercaseRole + "Name");
        //Vehicle vehicle = new Car("blue", "tesft", "test");
        //driver.associateVehicle(vehicle);
        return driver;
    }

    private List<User> createClients(HashingStrategy hashingStrategy) {
        List<User> users = new ArrayList<>();
        for (int i = 0; i <= CLIENT_QUANTITY; i++) {
            users.add(createClient(hashingStrategy, i));
        }
        return users;
    }

    private User createClient(HashingStrategy hashingStrategy, int clientNumber) {
        return updateBaseAttributes(new User(), Role.CLIENT, hashingStrategy, String.valueOf(clientNumber));
    }

    private List<User> createAdministrators(HashingStrategy hashingStrategy) {
        List<User> users = new ArrayList<>();
        for (int i = 0; i <= ADMINISTRATOR_QUANTITY; i++) {
            users.add(createAdministrator(hashingStrategy, i));
        }
        return users;
    }

    private User createAdministrator(HashingStrategy hashingStrategy, int clientNumber) {
        return updateBaseAttributes(new Administrator(), Role.ADMINISTRATOR,
                hashingStrategy, String.valueOf(clientNumber));
    }

    private User updateBaseAttributes(User user, Role role, HashingStrategy hashingStrategy, String indexNumber) {
        String lowercaseRole = role.name().toLowerCase();
        user.setRole(role);
        user.setUsername(lowercaseRole + indexNumber + "Username");
        user.setPassword(lowercaseRole + indexNumber + "Password", hashingStrategy);
        user.setEmailAddress(lowercaseRole + indexNumber + "@ultaxi.ca");
        return user;
    }
}
