package ca.ulaval.glo4003.ws.domain.user;

import ca.ulaval.glo4003.ws.api.user.dto.UserDto;
import ca.ulaval.glo4003.ws.domain.messaging.Message;
import ca.ulaval.glo4003.ws.domain.messaging.MessageProducerService;

import java.util.logging.Logger;

public class UserService {

    private Logger logger = Logger.getLogger(UserService.class.getName());

    private UserRepository userRepository;
    private UserAssembler userAssembler;
    private MessageProducerService messagingService;

    public UserService(UserRepository userRepository, UserAssembler userAssembler, MessageProducerService messagingService) {
        this.userRepository = userRepository;
        this.userAssembler = userAssembler;
        this.messagingService = messagingService;
    }

    public void addUser(UserDto userDto) {
        logger.info(String.format("Add new user %s", userDto));
        User user = userAssembler.create(userDto);
        userRepository.save(user);

        Message registrationMessage = new Message(user.getEmail(), "Registration");
        messagingService.enqueueMessage(registrationMessage);
    }
}
