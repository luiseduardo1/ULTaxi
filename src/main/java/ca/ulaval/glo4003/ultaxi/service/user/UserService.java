package ca.ulaval.glo4003.ultaxi.service.user;

import ca.ulaval.glo4003.ultaxi.domain.messaging.MessagingTaskProducer;
import ca.ulaval.glo4003.ultaxi.domain.messaging.email.EmailSender;
import ca.ulaval.glo4003.ultaxi.domain.messaging.messagingtask.MessagingTask;
import ca.ulaval.glo4003.ultaxi.domain.messaging.messagingtask.SendRegistrationEmailTask;
import ca.ulaval.glo4003.ultaxi.domain.user.Role;
import ca.ulaval.glo4003.ultaxi.domain.user.TokenManager;
import ca.ulaval.glo4003.ultaxi.domain.user.User;
import ca.ulaval.glo4003.ultaxi.domain.user.UserRepository;
import ca.ulaval.glo4003.ultaxi.transfer.user.UserAssembler;
import ca.ulaval.glo4003.ultaxi.transfer.user.UserDto;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

public class UserService {

    private final Logger logger = Logger.getLogger(UserService.class.getName());

    private static final List<String> AUTHENTICATION_SCHEMES = Collections.unmodifiableList(Arrays.asList("Bearer",
            "Basic",
            "Digest"));

    private final UserRepository userRepository;
    private final UserAssembler userAssembler;
    private final MessagingTaskProducer messagingTaskProducer;
    private final EmailSender emailSender;
    private final TokenManager tokenManager;

    public UserService(UserRepository userRepository, UserAssembler userAssembler,
                       MessagingTaskProducer messagingTaskProducer, EmailSender emailSender,
                       TokenManager tokenManager) {
        this.userRepository = userRepository;
        this.userAssembler = userAssembler;
        this.messagingTaskProducer = messagingTaskProducer;
        this.emailSender = emailSender;
        this.tokenManager = tokenManager;
    }

    public void addUser(UserDto userDto) {
        logger.info(String.format("Add new user %s.", userDto));
        User user = userAssembler.create(userDto);
        user.setRole(Role.CLIENT);
        userRepository.save(user);

        MessagingTask messagingTask = new SendRegistrationEmailTask(user.getEmailAddress(), user.getUsername(),
                emailSender);
        messagingTaskProducer.send(messagingTask);
    }

    public void updateClient(UserDto userDto, String userToken) {
        User user = getUserFromToken(userToken);
        userDto.setUsername(user.getUsername());
        logger.info(String.format("Updating a user with infos: %s", userDto));
        user = userAssembler.create(userDto);
        user.setRole(Role.CLIENT);
        userRepository.update(user);
    }

    public User getUserFromToken(String userToken) {
        String username = tokenManager.getUsername(extractToken(userToken));
        return userRepository.findByUsername(username);
    }

    private String extractToken(String unparsedToken) {
        String token = unparsedToken;
        for (String authenticationScheme : AUTHENTICATION_SCHEMES) {
            if (unparsedToken != null && unparsedToken.toLowerCase().contains(authenticationScheme.toLowerCase())) {
                token = unparsedToken.substring(authenticationScheme.length()).trim();
            }
        }

        return token;
    }
}
