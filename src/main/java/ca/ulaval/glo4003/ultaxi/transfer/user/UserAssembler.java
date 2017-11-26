package ca.ulaval.glo4003.ultaxi.transfer.user;

import ca.ulaval.glo4003.ultaxi.domain.user.PhoneNumber;
import ca.ulaval.glo4003.ultaxi.domain.user.User;
import ca.ulaval.glo4003.ultaxi.utils.hashing.HashingStrategy;

public class UserAssembler {

    private final HashingStrategy hashingStrategy;

    public UserAssembler(HashingStrategy hashingStrategy) {
        this.hashingStrategy = hashingStrategy;
    }

    public User create(UserDto userDto) {
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword(), hashingStrategy);
        user.setEmailAddress(userDto.getEmail());
        if (userDto.getPhoneNumber() != null) {
            PhoneNumber phoneNumber = new PhoneNumber(userDto.getPhoneNumber());
            user.setPhoneNumber(phoneNumber);
        }
        return user;
    }

    public UserDto create(User user) {
        UserDto userDto = new UserDto();
        userDto.setUsername(user.getUsername());
        userDto.setPassword(user.getPassword());
        userDto.setEmail(user.getEmailAddress());
        if (user.getPhoneNumber().getNumber() != null) {
            userDto.setPhoneNumber(user.getPhoneNumber().getNumber());
        }
        return userDto;
    }
}
