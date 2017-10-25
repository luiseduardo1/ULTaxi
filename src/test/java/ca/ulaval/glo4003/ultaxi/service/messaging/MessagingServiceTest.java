package ca.ulaval.glo4003.ultaxi.service.messaging;

import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.verify;

import ca.ulaval.glo4003.ultaxi.domain.messaging.Message;
import ca.ulaval.glo4003.ultaxi.domain.messaging.MessageQueueConsumer;
import ca.ulaval.glo4003.ultaxi.domain.messaging.email.Email;
import ca.ulaval.glo4003.ultaxi.domain.messaging.email.exception.InvalidEmailTypeException;
import ca.ulaval.glo4003.ultaxi.infrastructure.messaging.EmailSender;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

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
