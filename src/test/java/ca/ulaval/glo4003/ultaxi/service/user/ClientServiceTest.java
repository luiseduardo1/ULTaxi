package ca.ulaval.glo4003.ultaxi.service.user;

import static org.mockito.BDDMockito.verify;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4003.ultaxi.domain.messaging.MessagingTaskProducer;
import ca.ulaval.glo4003.ultaxi.domain.messaging.email.EmailSender;
import ca.ulaval.glo4003.ultaxi.domain.messaging.messagingtask.MessagingTask;
import ca.ulaval.glo4003.ultaxi.domain.user.UserRepository;
import ca.ulaval.glo4003.ultaxi.domain.user.client.Client;
import ca.ulaval.glo4003.ultaxi.service.user.client.ClientService;
import ca.ulaval.glo4003.ultaxi.transfer.user.client.ClientAssembler;
import ca.ulaval.glo4003.ultaxi.transfer.user.client.ClientDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
/*
@RunWith(MockitoJUnitRunner.class)
public class ClientServiceTest {

    private final static String A_VALID_USERNAME = "Valid username";
    private static final String A_VALID_TOKEN = "Valid token";
    private final static String A_VALID_EMAIL = "Valid email";

    @Mock
    private Client client;
    @Mock
    private ClientDto clientDto;
    @Mock
    private ClientAssembler clientAssembler;
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserAuthenticationService userAuthenticationService;
    @Mock
    private MessagingTaskProducer messagingTaskProducer;
    @Mock
    private EmailSender emailSender;

    private ClientService clientService;

    @Before
    public void setUp() throws Exception {
        willReturn(client).given(clientAssembler).create(clientDto);
        willReturn(A_VALID_USERNAME).given(client).getUsername();
        willReturn(A_VALID_EMAIL).given(client).getEmailAddress();
        clientService = new ClientService(userRepository, clientAssembler, messagingTaskProducer, emailSender,
                                          userAuthenticationService);
    }

    @Test
    public void givenClientWithValidName_whenAddClient_thenClientIsAdded() {
        clientService.addClient(clientDto);

        verify(userRepository).save(client);
    }

    @Test
    public void givenANewClient_whenAddClient_thenMessagingTaskProducerIsCalled() {
        clientService.addClient(clientDto);

        verify(messagingTaskProducer).send(any(MessagingTask.class));
    }

    @Test
    public void givenAClient_whenUpdateClient_thenClientIsUpdated() {
        when(userAuthenticationService.getUserFromToken(A_VALID_TOKEN)).thenReturn(client);
        willReturn(client).given(clientAssembler).create(clientDto);

        clientService.updateClient(clientDto, A_VALID_TOKEN);

        verify(userRepository).update(client);
    }
}*/