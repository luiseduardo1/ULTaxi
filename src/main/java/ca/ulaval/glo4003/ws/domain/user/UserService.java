package ca.ulaval.glo4003.ws.domain.user;

import ca.ulaval.glo4003.ws.api.user.dto.UserDto;
import ca.ulaval.glo4003.ws.domain.email.EmailService;

import java.util.logging.Logger;

public class UserService {

    private Logger logger = Logger.getLogger(UserService.class.getName());

    private UserRepository userRepository;
    private UserAssembler userAssembler;
    private EmailService emailService;

    public UserService(UserRepository userRepository, UserAssembler userAssembler, EmailService emailService) {
        this.userRepository = userRepository;
        this.userAssembler = userAssembler;
        this.emailService = emailService;
    }

    public void addUser(UserDto userDto) {
        logger.info(String.format("Add new user %s", userDto));
        User user = userAssembler.create(userDto);
        userRepository.save(user);

        emailService.sendRegistrationEmail(user.getEmail());
    }
}
