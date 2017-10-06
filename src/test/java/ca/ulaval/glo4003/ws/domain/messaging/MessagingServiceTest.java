package ca.ulaval.glo4003.ws.domain.messaging;

import ca.ulaval.glo4003.ws.domain.messaging.email.Email;
import ca.ulaval.glo4003.ws.domain.messaging.email.InvalidEmailTypeException;
import ca.ulaval.glo4003.ws.infrastructure.messaging.EmailSender;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.verify;

;

@RunWith(MockitoJUnitRunner.class)
public class MessagingServiceTest {

    private static final String REASON = "Registration";
    MessagingService messagingService;

    @Mock
    Message message;
    @Mock
    Email email;
    @Mock
    MessageQueueConsumer messageQueueConsumer;
    @Mock
    EmailSender emailSender;

    @Before
    public void setUp() throws Exception {
        this.messagingService = new MessagingService(messageQueueConsumer, emailSender);
    }

    @Test
    public void givenAEmailMessageToBeSent_whenSendMessage_thenEmailSenderIsCalled()
        throws InterruptedException, InvalidEmailTypeException {
        willReturn(message).given(messageQueueConsumer).checkForMessage();
        willReturn(email).given(messageQueueConsumer).convertToEmail(message);

        messagingService.sendMessage();

        verify(emailSender).sendEmail(email);
    }

    @Test
    public void givenAMessageToBeSent_whenSendMessageSuccessfully_thenMessageIsRemovedFromQueue()
        throws InterruptedException {
        willReturn(message).given(messageQueueConsumer).checkForMessage();

        messagingService.sendMessage();

        verify(messageQueueConsumer).removeMessage(message);
    }
}
