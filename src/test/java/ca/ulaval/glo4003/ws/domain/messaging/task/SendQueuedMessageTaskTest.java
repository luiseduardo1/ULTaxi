package ca.ulaval.glo4003.ws.domain.messaging.task;

import ca.ulaval.glo4003.ws.domain.messaging.Email;
import ca.ulaval.glo4003.ws.domain.messaging.Message;
import ca.ulaval.glo4003.ws.domain.messaging.MessageQueue;
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
public class SendQueuedMessageTaskTest {

    private static final String SEND_TO = "test@example.com";
    private static final String REASON = "Registration";
    SendQueuedMessageTask sendQueuedMessageTask;

    @Mock
    EmailSender emailSender;
    @Mock
    MessageQueue messageQueue;
    private Message message;

    @Before
    public void setUp() throws Exception {
        sendQueuedMessageTask = new SendQueuedMessageTask(messageQueue, emailSender);
        message = new Message(SEND_TO, REASON);
    }

    @Test
    public void givenARegistrationMessageToBeSent_whenSendMessage_thenEmailSenderIsCalled()
        throws InterruptedException {
        willReturn(false).given(messageQueue).isEmpty();
        willReturn(message).given(messageQueue).peek();
        doNothing().when(messageQueue).dequeue(message);

        sendQueuedMessageTask.sendMessage();

        verify(emailSender).sendEmail(any(Email.class));
    }

}
