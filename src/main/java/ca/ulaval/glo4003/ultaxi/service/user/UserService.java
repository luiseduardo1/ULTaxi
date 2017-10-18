package ca.ulaval.glo4003.ultaxi.service.user;

import ca.ulaval.glo4003.ultaxi.domain.messaging.Message;
import ca.ulaval.glo4003.ultaxi.domain.messaging.MessageQueueProducer;
import ca.ulaval.glo4003.ultaxi.domain.user.Role;
import ca.ulaval.glo4003.ultaxi.domain.user.User;
import ca.ulaval.glo4003.ultaxi.domain.user.UserRepository;
import ca.ulaval.glo4003.ultaxi.transfer.user.UserAssembler;
import ca.ulaval.glo4003.ultaxi.transfer.user.UserDto;

import java.util.logging.Logger;

public class UserService {

    private Logger logger = Logger.getLogger(UserService.class.getName());

    private UserRepository userRepository;
    private UserAssembler userAssembler;
    private MessageQueueProducer messageQueueProducer;

    public UserService(UserRepository userRepository,
                       UserAssembler userAssembler,
                       MessageQueueProducer messageQueueProducer) {
        this.userRepository = userRepository;
        this.userAssembler = userAssembler;
        this.messageQueueProducer = messageQueueProducer;
    }

    public void addUser(UserDto userDto) {
        logger.info(String.format("Add new user %s.", userDto));
        User user = userAssembler.create(userDto);
        user.setRole(Role.Client);
        userRepository.save(user);
        Message registrationMessage = new Message(user.getEmailAddress(), "Registration");
        messageQueueProducer.send(registrationMessage);
    }
}
