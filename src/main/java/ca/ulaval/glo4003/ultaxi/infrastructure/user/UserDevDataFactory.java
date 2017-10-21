package ca.ulaval.glo4003.ultaxi.infrastructure.user;

import ca.ulaval.glo4003.ultaxi.domain.user.Role;
import ca.ulaval.glo4003.ultaxi.domain.user.User;
import ca.ulaval.glo4003.ultaxi.utils.hashing.HashingStrategy;
import jersey.repackaged.com.google.common.collect.Lists;

import java.util.List;

public class UserDevDataFactory {

    public List<User> createMockData(HashingStrategy hashingStrategy) {
        List<User> users = Lists.newArrayList();
        for (Role role : Role.values()) {
            users.add(
                createGenericRoleUser(role, hashingStrategy)
            );
        }
        return users;
    }

    private User createGenericRoleUser(Role role, HashingStrategy hashingStrategy) {
        String lowercaseRole = role.name().toLowerCase();
        User user = new User();
        user.setRole(role);
        user.setUsername(lowercaseRole + "Username");
        user.setPassword(lowercaseRole + "Password", hashingStrategy);
        user.setEmailAddress(lowercaseRole + "@ultaxi.ca");
        return user;
    }
}
