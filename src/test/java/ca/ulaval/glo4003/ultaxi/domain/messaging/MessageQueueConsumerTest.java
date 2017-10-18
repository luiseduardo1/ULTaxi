package ca.ulaval.glo4003.ultaxi.domain.messaging;

import ca.ulaval.glo4003.ultaxi.domain.messaging.email.exception.InvalidEmailTypeException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;


@RunWith(MockitoJUnitRunner.class)
public class MessageQueueConsumerTest {

    private static final String SEND_TO = "test@example.com";
    private static final String REASON = "Registration";
    private static final String INVALID_REASON = "Invalid";
    MessageQueueConsumer messageQueueConsumer;

    @Mock
    MessageQueue messageQueue;

    private Message message;

    @Before
    public void setUp() throws Exception {
        messageQueueConsumer = new MessageQueueConsumer(messageQueue);
        message = new Message(SEND_TO, REASON);
    }

    @Test
    public void givenAMessageInQueue_whenCheckForMessage_thenPeekFunctionIsCalledOnQueue() {
        messageQueueConsumer.checkForMessage();

        verify(messageQueue).peek();
    }

    @Test
    public void givenAMessageInQueue_whenRemoveMessage_theDequeueFunctionIsCalledOnQueue() {
        message = messageQueueConsumer.checkForMessage();

        messageQueueConsumer.removeMessage(message);

        verify(messageQueue).dequeue(message);
    }

    @Test(expected = InvalidEmailTypeException.class)
    public void givenAnInvalidEmailType_whenConvertToEmail_thenShouldThrowException() throws InvalidEmailTypeException {
        Message invalidMessage = new Message(SEND_TO, INVALID_REASON);
        messageQueueConsumer.convertToEmail(invalidMessage);
    }

}
