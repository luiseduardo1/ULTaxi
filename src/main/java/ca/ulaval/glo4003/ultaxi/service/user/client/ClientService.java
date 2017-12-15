package ca.ulaval.glo4003.ultaxi.service.user.client;

import ca.ulaval.glo4003.ultaxi.domain.messaging.MessagingTaskProducer;
import ca.ulaval.glo4003.ultaxi.domain.messaging.email.EmailSender;
import ca.ulaval.glo4003.ultaxi.domain.messaging.messagingtask.MessagingTask;
import ca.ulaval.glo4003.ultaxi.domain.messaging.messagingtask.SendRegistrationEmailTask;
import ca.ulaval.glo4003.ultaxi.domain.user.UserRepository;
import ca.ulaval.glo4003.ultaxi.domain.user.client.Client;
import ca.ulaval.glo4003.ultaxi.service.user.UserAuthenticationService;
import ca.ulaval.glo4003.ultaxi.transfer.user.UserPersistenceAssembler;
import ca.ulaval.glo4003.ultaxi.transfer.user.UserPersistenceDto;
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
    private final UserPersistenceAssembler userPersistenceAssembler;

    public ClientService(UserRepository userRepository, ClientAssembler clientAssembler,
        MessagingTaskProducer messagingTaskProducer, EmailSender emailSender,
        UserAuthenticationService userAuthenticationService,
                         UserPersistenceAssembler userPersistenceAssembler) {
        this.userRepository = userRepository;
        this.clientAssembler = clientAssembler;
        this.messagingTaskProducer = messagingTaskProducer;
        this.emailSender = emailSender;
        this.userAuthenticationService = userAuthenticationService;
        this.userPersistenceAssembler = userPersistenceAssembler;
    }

    public void addClient(ClientDto clientDto) {
        Client client = clientAssembler.create(clientDto);
        logger.info(String.format("Add new client %s.", clientDto));
        UserPersistenceDto userPersistenceDto = userPersistenceAssembler.create(client);
        userRepository.save(userPersistenceDto);

        MessagingTask messagingTask = new SendRegistrationEmailTask(
            client.getEmailAddress(), client.getUsername(), emailSender
        );
        messagingTaskProducer.send(messagingTask);
    }

    public void updateClient(ClientDto clientDto, String userToken) {
        clientAssembler
        Client client = (Client) userAuthenticationService.getUserFromToken(userToken);
        clientDto.setUsername(client.getUsername());
        logger.info(String.format("Updating a client with infos: %s", clientDto));
        client = clientAssembler.create(clientDto);
        UserPersistenceDto userPersistenceDto = userPersistenceAssembler.create(client);
        userRepository.update(userPersistenceDto);
    }
}
