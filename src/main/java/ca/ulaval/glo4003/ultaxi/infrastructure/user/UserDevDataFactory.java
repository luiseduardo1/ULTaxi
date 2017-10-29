package ca.ulaval.glo4003.ultaxi.infrastructure.user;

import ca.ulaval.glo4003.ultaxi.domain.user.Role;
import ca.ulaval.glo4003.ultaxi.domain.user.User;
import ca.ulaval.glo4003.ultaxi.domain.user.administrator.Administrator;
import ca.ulaval.glo4003.ultaxi.domain.user.driver.Driver;
import ca.ulaval.glo4003.ultaxi.utils.hashing.HashingStrategy;
import jersey.repackaged.com.google.common.collect.Lists;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class UserDevDataFactory {

    private final Map<Role, String> userTypesByRoles = new EnumMap<>(Role.class);

    public UserDevDataFactory() {
        userTypesByRoles.put(Role.DRIVER, Driver.class.getName());
        userTypesByRoles.put(Role.CLIENT, User.class.getName());
        userTypesByRoles.put(Role.ADMINISTRATOR, Administrator.class.getName());
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
        return users;
    }

    private User createGenericRoleUser(Role role, HashingStrategy hashingStrategy) throws Exception {
        String lowercaseRole = role.name().toLowerCase();
        User user = (User) Class.forName(userTypesByRoles.get(role)).newInstance();
        user.setRole(role);
        user.setUsername(lowercaseRole + "Username");
        user.setPassword(lowercaseRole + "Password", hashingStrategy);
        user.setEmailAddress(lowercaseRole + "@ultaxi.ca");
        return user;
    }
}
