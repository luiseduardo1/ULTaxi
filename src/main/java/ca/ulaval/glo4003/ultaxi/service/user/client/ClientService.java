package ca.ulaval.glo4003.ultaxi.service.user.client;

import ca.ulaval.glo4003.ultaxi.domain.messaging.MessagingTaskProducer;
import ca.ulaval.glo4003.ultaxi.domain.messaging.email.EmailSender;
import ca.ulaval.glo4003.ultaxi.domain.messaging.messagingtask.MessagingTask;
import ca.ulaval.glo4003.ultaxi.domain.messaging.messagingtask.SendRegistrationEmailTask;
import ca.ulaval.glo4003.ultaxi.domain.user.UserRepository;
import ca.ulaval.glo4003.ultaxi.domain.user.client.Client;
import ca.ulaval.glo4003.ultaxi.domain.user.exception.InvalidEmailAddressException;
import ca.ulaval.glo4003.ultaxi.domain.user.exception.InvalidHashingStrategyException;
import ca.ulaval.glo4003.ultaxi.domain.user.exception.InvalidPasswordException;
import ca.ulaval.glo4003.ultaxi.domain.user.exception.InvalidPhoneNumberException;
import ca.ulaval.glo4003.ultaxi.domain.user.exception.InvalidUsernameException;
import ca.ulaval.glo4003.ultaxi.domain.user.exception.NonExistentUserException;
import ca.ulaval.glo4003.ultaxi.domain.user.exception.UserAlreadyExistsException;
import ca.ulaval.glo4003.ultaxi.infrastructure.user.jwt.exception.InvalidTokenException;
import ca.ulaval.glo4003.ultaxi.service.user.UserAuthenticationService;
import ca.ulaval.glo4003.ultaxi.transfer.user.client.ClientAssembler;
import ca.ulaval.glo4003.ultaxi.transfer.user.client.ClientDto;

import java.util.logging.Logger;

public class ClientService {

    private final Logger logger = Logger.getLogger(ClientService.class.getName());

    private final UserRepository userRepository;
    private final ClientAssembler clientAssembler;
    private final MessagingTaskProducer messagingTaskProducer;
    private final EmailSender emailSender;
    private final UserAuthenticationService userAuthenticationService;

    public ClientService(UserRepository userRepository, ClientAssembler clientAssembler,
        MessagingTaskProducer messagingTaskProducer, EmailSender emailSender,
        UserAuthenticationService userAuthenticationService) {
        this.userRepository = userRepository;
        this.clientAssembler = clientAssembler;
        this.messagingTaskProducer = messagingTaskProducer;
        this.emailSender = emailSender;
        this.userAuthenticationService = userAuthenticationService;
    }

    public void addClient(ClientDto clientDto) throws InvalidEmailAddressException, InvalidHashingStrategyException,
        InvalidPasswordException, InvalidPhoneNumberException, InvalidUsernameException, UserAlreadyExistsException {
        Client client = clientAssembler.create(clientDto);
        logger.info(String.format("Add new client %s.", clientDto));
        userRepository.save(client);
        MessagingTask messagingTask = new SendRegistrationEmailTask(
            client.getEmailAddress(), client.getUsername(), emailSender
        );
        messagingTaskProducer.send(messagingTask);

    }

    public void updateClient(ClientDto clientDto, String userToken) throws InvalidEmailAddressException,
        InvalidHashingStrategyException, InvalidPasswordException, InvalidPhoneNumberException,
        InvalidUsernameException, NonExistentUserException, InvalidTokenException {
        Client client = (Client) userAuthenticationService.getUserFromToken(userToken);
        clientDto.setUsername(client.getUsername());
        logger.info(String.format("Updating a client with infos: %s", clientDto));
        client = clientAssembler.create(clientDto);
        userRepository.update(client);
    }
}
