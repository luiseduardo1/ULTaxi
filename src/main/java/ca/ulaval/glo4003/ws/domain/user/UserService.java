package ca.ulaval.glo4003.ws.domain.user;

import ca.ulaval.glo4003.ws.api.user.dto.UserDto;
import ca.ulaval.glo4003.ws.domain.messaging.Message;
import ca.ulaval.glo4003.ws.domain.messaging.MessageQueueProducer;

import java.util.logging.Logger;

public class UserService {

    private Logger logger = Logger.getLogger(UserService.class.getName());

    private UserRepository userRepository;
    private UserAssembler userAssembler;
    private MessageQueueProducer messageQueueProducer;

    public UserService(UserRepository userRepository, UserAssembler userAssembler,
        MessageQueueProducer messageQueueProducer) {
        this.userRepository = userRepository;
        this.userAssembler = userAssembler;
        this.messageQueueProducer = messageQueueProducer;
    }

    public void addUser(UserDto userDto) {
        logger.info(String.format("Add new user %s", userDto));
        User user = userAssembler.create(userDto);
        userRepository.save(user);

        Message registrationMessage = new Message(user.getEmailAddress(), "Registration");
        messageQueueProducer.send(registrationMessage);
    }
}
