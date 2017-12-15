package ca.ulaval.glo4003.ultaxi.domain.user;

import ca.ulaval.glo4003.ultaxi.domain.user.administrator.Administrator;
import ca.ulaval.glo4003.ultaxi.domain.user.client.Client;
import ca.ulaval.glo4003.ultaxi.domain.user.driver.Driver;
import ca.ulaval.glo4003.ultaxi.domain.user.exception.InvalidUserRoleException;
import ca.ulaval.glo4003.ultaxi.utils.hashing.HashingStrategy;

public final class UserFactory {

    public static User getUser(String username, String password, PhoneNumber number,
                               String emailAddress, HashingStrategy hashingStrategy, Role role,
                               String firstName, String lastName,
                               SocialInsuranceNumber socialInsuranceNumber) {

        switch (role) {
            case DRIVER:
                return new Driver(username, password, number, emailAddress, hashingStrategy,
                    firstName, lastName, socialInsuranceNumber);
            case ADMINISTRATOR:
                return new Administrator(username, password, number, emailAddress, hashingStrategy);
            case CLIENT:
                return new Client(username, password, number, emailAddress, hashingStrategy);
            default:
                throw new InvalidUserRoleException(
                    String.format("%s is not a valid role", role)
                );
        }
    }

}
