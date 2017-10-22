package ca.ulaval.glo4003.ultaxi.service.user;

import ca.ulaval.glo4003.ultaxi.domain.messaging.TaskQueueProducer;
import ca.ulaval.glo4003.ultaxi.domain.messaging.tasks.SendRegistrationEmailTask;
import ca.ulaval.glo4003.ultaxi.domain.messaging.tasks.Task;
import ca.ulaval.glo4003.ultaxi.domain.user.Role;
import ca.ulaval.glo4003.ultaxi.domain.user.User;
import ca.ulaval.glo4003.ultaxi.domain.user.UserRepository;
import ca.ulaval.glo4003.ultaxi.infrastructure.messaging.EmailSender;
import ca.ulaval.glo4003.ultaxi.transfer.user.UserAssembler;
import ca.ulaval.glo4003.ultaxi.transfer.user.UserDto;

import java.util.logging.Logger;

public class UserService {

    private final Logger logger = Logger.getLogger(UserService.class.getName());

    private UserRepository userRepository;
    private UserAssembler userAssembler;
    private TaskQueueProducer taskQueueProducer;
    private EmailSender emailSender;

    public UserService(UserRepository userRepository,
                       UserAssembler userAssembler,
                       TaskQueueProducer taskQueueProducer,
                       EmailSender emailSender) {
        this.userRepository = userRepository;
        this.userAssembler = userAssembler;
        this.taskQueueProducer = taskQueueProducer;
        this.emailSender = emailSender;
    }

    public void addUser(UserDto userDto) {
        logger.info(String.format("Add new user %s.", userDto));
        User user = userAssembler.create(userDto);
        user.setRole(Role.Client);
        userRepository.save(user);

        Task task = new SendRegistrationEmailTask(user.getEmailAddress(), user.getUsername(), emailSender);
        taskQueueProducer.send(task);
    }
}
