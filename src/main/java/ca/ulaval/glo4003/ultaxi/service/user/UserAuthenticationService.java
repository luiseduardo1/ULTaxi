package ca.ulaval.glo4003.ultaxi.service.user;

import ca.ulaval.glo4003.ultaxi.domain.user.User;
import ca.ulaval.glo4003.ultaxi.domain.user.UserRepository;
import ca.ulaval.glo4003.ultaxi.domain.user.exception.InvalidCredentialsException;
import ca.ulaval.glo4003.ultaxi.transfer.user.UserAssembler;
import ca.ulaval.glo4003.ultaxi.transfer.user.UserDto;

public class UserAuthenticationService {

    private final UserRepository userRepository;
    private final UserAssembler userAssembler;

    public UserAuthenticationService(UserRepository userRepository, UserAssembler userAssembler) {
        this.userRepository = userRepository;
        this.userAssembler = userAssembler;
    }

    public void authenticate(UserDto userDto) {
        User user = userAssembler.create(userDto);
        User validUser = userRepository.findByUsername(user.getUsername());
        if (validUser == null || !validUser.areCredentialsValid(user.getUsername(), userDto.getPassword())) {
            throw new InvalidCredentialsException("Credentials are invalid.");
        }
    }
}
