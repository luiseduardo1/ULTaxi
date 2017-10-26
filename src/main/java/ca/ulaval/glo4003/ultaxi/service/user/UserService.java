package ca.ulaval.glo4003.ultaxi.service.user;

import ca.ulaval.glo4003.ultaxi.domain.messaging.Message;
import ca.ulaval.glo4003.ultaxi.domain.messaging.MessageQueueProducer;
import ca.ulaval.glo4003.ultaxi.domain.messaging.Reason;
import ca.ulaval.glo4003.ultaxi.domain.user.Role;
import ca.ulaval.glo4003.ultaxi.domain.user.User;
import ca.ulaval.glo4003.ultaxi.domain.user.UserRepository;
import ca.ulaval.glo4003.ultaxi.transfer.user.UserAssembler;
import ca.ulaval.glo4003.ultaxi.transfer.user.UserDto;

import java.util.logging.Logger;

public class UserService {

    private final Logger logger = Logger.getLogger(UserService.class.getName());

    private final UserRepository userRepository;
    private final UserAssembler userAssembler;
    private final MessageQueueProducer messageQueueProducer;

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
        Message registrationMessage = new Message(user.getEmailAddress(), Reason.Registration);
        messageQueueProducer.send(registrationMessage);
    }

    public void updateUser(UserDto userDto) {
        logger.info(String.format("Updating a user with infos: %s", userDto));
        User user = userAssembler.create(userDto);
        userRepository.update(user);
    }
}
