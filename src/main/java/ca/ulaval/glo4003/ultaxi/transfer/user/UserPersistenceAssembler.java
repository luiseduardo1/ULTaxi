package ca.ulaval.glo4003.ultaxi.transfer.user;

import ca.ulaval.glo4003.ultaxi.domain.user.User;
import ca.ulaval.glo4003.ultaxi.domain.user.UserFactory;
import ca.ulaval.glo4003.ultaxi.domain.user.administrator.Administrator;
import ca.ulaval.glo4003.ultaxi.domain.user.client.Client;
import ca.ulaval.glo4003.ultaxi.domain.user.driver.Driver;

public class UserPersistenceAssembler {

    public User create(UserPersistenceDto userPersistenceDto) {
        return UserFactory.getUser(
            userPersistenceDto.getUsername(),
            userPersistenceDto.getPassword(),
            userPersistenceDto.getPhoneNumber(),
            userPersistenceDto.getEmailAddress(),
            userPersistenceDto.getHashingStrategy(),
            userPersistenceDto.getRole(),
            userPersistenceDto.getFirstName(),
            userPersistenceDto.getLastName(),
            userPersistenceDto.getSocialInsuranceNumber());
    }

    public UserPersistenceDto create(Driver driver) {
        UserPersistenceDto userPersistenceDto = new UserPersistenceDto();

        userPersistenceDto.setUsername(driver.getUsername());
        userPersistenceDto.setPassword(driver.getPassword());
        userPersistenceDto.setHashingStrategy(driver.getHashingStrategy());
        userPersistenceDto.setFirstName(driver.getFirstName());
        userPersistenceDto.setLastName(driver.getLastName());
        userPersistenceDto.setEmailAddress(driver.getEmailAddress());
        userPersistenceDto.setPhoneNumber(driver.getPhoneNumber());
        userPersistenceDto.setSocialInsuranceNumber(driver.getSocialInsuranceNumber());
        userPersistenceDto.setRole(driver.getRole());
        userPersistenceDto.setVehicle(driver.getVehicle());
        userPersistenceDto.setCurrentTransportRequestId(driver.getCurrentTransportRequestId());

        return userPersistenceDto;
    }

    public UserPersistenceDto create(Client client) {
        UserPersistenceDto userPersistenceDto = new UserPersistenceDto();

        userPersistenceDto.setEmailAddress(client.getEmailAddress());
        userPersistenceDto.setHashingStrategy(client.getHashingStrategy());
        userPersistenceDto.setRole(client.getRole());
        userPersistenceDto.setPassword(client.getPassword());
        userPersistenceDto.setUsername(client.getUsername());
        userPersistenceDto.setPhoneNumber(client.getPhoneNumber());

        return userPersistenceDto;
    }

    public UserPersistenceDto create(Administrator administrator) {
        UserPersistenceDto userPersistenceDto = new UserPersistenceDto();

        userPersistenceDto.setUsername(administrator.getUsername());
        userPersistenceDto.setPassword(administrator.getPassword());
        userPersistenceDto.setHashingStrategy(administrator.getHashingStrategy());
        userPersistenceDto.setRole(administrator.getRole());
        userPersistenceDto.setPhoneNumber(administrator.getPhoneNumber());
        userPersistenceDto.setEmailAddress(administrator.getEmailAddress());

        return userPersistenceDto;
    }
}
