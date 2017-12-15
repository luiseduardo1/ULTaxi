package ca.ulaval.glo4003.ultaxi.infrastructure.user;

import ca.ulaval.glo4003.ultaxi.domain.user.PhoneNumber;
import ca.ulaval.glo4003.ultaxi.domain.user.Role;
import ca.ulaval.glo4003.ultaxi.domain.user.SocialInsuranceNumber;
import ca.ulaval.glo4003.ultaxi.domain.user.User;
import ca.ulaval.glo4003.ultaxi.domain.user.administrator.Administrator;
import ca.ulaval.glo4003.ultaxi.domain.user.client.Client;
import ca.ulaval.glo4003.ultaxi.domain.user.driver.Driver;
import ca.ulaval.glo4003.ultaxi.transfer.user.UserPersistenceDto;
import ca.ulaval.glo4003.ultaxi.utils.hashing.HashingStrategy;
import jersey.repackaged.com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class UserDevDataFactory {

    private static final String PHONE_NUMBER = "2342355678";
    private static final String SOCIAL_INSURANCE_NUMBER = "972487086";
    private static final String USERNAME_SUFFIX = "Username";
    private static final String PASSWORD_SUFFIX = "Password";
    private static final String EMAIL_SUFFIX = "@ultaxi.ca";
    private static final String FIRST_NAME_SUFFIX = "FirstName";
    private static final String LAST_NAME_SUFFIX = "LastName";

    private final Map<Role, Function<HashingStrategy, List<UserPersistenceDto>>> userTypesByRoles = new HashMap<>();

    public UserDevDataFactory() {
        userTypesByRoles.put(Role.DRIVER, this::createDrivers);
        userTypesByRoles.put(Role.CLIENT, this::createClients);
        userTypesByRoles.put(Role.ADMINISTRATOR, this::createAdministrators);
    }

    public List<UserPersistenceDto> createMockData(HashingStrategy hashingStrategy) throws Exception {
        List<UserPersistenceDto> users = Lists.newArrayList();
        for (Role role : Role.values()) {
            users.addAll(
                createGenericRoleUsers(role, hashingStrategy)
            );
        }
        return users;
    }

    private List<UserPersistenceDto> createGenericRoleUsers(Role role, HashingStrategy hashingStrategy) throws Exception {
        return userTypesByRoles.get(role).apply(hashingStrategy);
    }

    private List<UserPersistenceDto> createAdministrators(HashingStrategy hashingStrategy) {
        List<UserPersistenceDto> administrators = new ArrayList<>();
        administrators.add(createAdministrator(hashingStrategy));
        return administrators;
    }

    private UserPersistenceDto createAdministrator(HashingStrategy hashingStrategy) {
        PhoneNumber phoneNumber = new PhoneNumber(PHONE_NUMBER);
        String administratorPrefix = generateUserPrefixString(Role.ADMINISTRATOR);
        UserPersistenceDto administrator = new UserPersistenceDto(
            administratorPrefix + USERNAME_SUFFIX,
            administratorPrefix + PASSWORD_SUFFIX,
            phoneNumber,
            administratorPrefix + EMAIL_SUFFIX,
            hashingStrategy
        );
        return administrator;
    }

    private List<UserPersistenceDto> createDrivers(HashingStrategy hashingStrategy) {
        List<UserPersistenceDto> drivers = new ArrayList<>();
        drivers.add(createDriver(hashingStrategy));
        return drivers;
    }

    private UserPersistenceDto createDriver(HashingStrategy hashingStrategy) {
        PhoneNumber phoneNumber = new PhoneNumber(PHONE_NUMBER);
        SocialInsuranceNumber socialInsuranceNumber = new SocialInsuranceNumber(SOCIAL_INSURANCE_NUMBER);
        String driverPrefix = generateUserPrefixString(Role.DRIVER);
        UserPersistenceDto driver = new UserPersistenceDto(
            driverPrefix + USERNAME_SUFFIX,
            driverPrefix + PASSWORD_SUFFIX,
            phoneNumber,
            driverPrefix + EMAIL_SUFFIX,
            hashingStrategy,
            driverPrefix + FIRST_NAME_SUFFIX,
            driverPrefix + LAST_NAME_SUFFIX,
            socialInsuranceNumber
        );
        return driver;
    }

    private List<UserPersistenceDto> createClients(HashingStrategy hashingStrategy) {
        List<UserPersistenceDto> clients = new ArrayList<>();
        clients.add(createClient(hashingStrategy));
        return clients;
    }

    private UserPersistenceDto createClient(HashingStrategy hashingStrategy) {
        PhoneNumber phoneNumber = new PhoneNumber(PHONE_NUMBER);
        String clientPrefix = generateUserPrefixString(Role.CLIENT);
        UserPersistenceDto client = new UserPersistenceDto(
            clientPrefix + USERNAME_SUFFIX,
            clientPrefix + PASSWORD_SUFFIX,
            phoneNumber,
            clientPrefix + EMAIL_SUFFIX,
            hashingStrategy
        );
        return client;
    }

    private String generateUserPrefixString(Role role) {
        return role.name().toLowerCase();
    }
}
