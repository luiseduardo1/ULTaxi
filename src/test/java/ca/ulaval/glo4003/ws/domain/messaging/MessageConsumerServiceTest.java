package ca.ulaval.glo4003.ws.domain.messaging;

import ca.ulaval.glo4003.ws.infrastructure.messaging.EmailSender;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class MessageConsumerServiceTest {

    private static final String SEND_TO = "test@example.com";
    private static final String REASON = "Registration";
    private Message message;
    private MessageConsumerService messageConsumerService;

    @Mock
    private MessageQueue messageQueue;
    @Mock
    private EmailSender emailSender;

    @Before
    public void setUp() throws Exception {
        messageConsumerService = new MessageConsumerService(messageQueue, emailSender);
        message = new Message(SEND_TO, REASON);
    }

    @Test
    public void givenARegistrationMessageToBeSent_whenSendMessage_thenEmailSenderIsCalled()
        throws InterruptedException {
        willReturn(false).given(messageQueue).isEmpty();
        willReturn(message).given(messageQueue).peek();
        doNothing().when(messageQueue).dequeue(message);

        messageConsumerService.sendMessage();

        verify(emailSender).sendEmail(any(Email.class));
    }
}
