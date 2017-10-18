package ca.ulaval.glo4003.ultaxi.domain.messaging;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class MessageQueueProducerTest {

    @Mock
    private MessageQueue messageQueue;
    @Mock
    private Message message;

    private MessageQueueProducer messageQueueProducer;

    @Before
    public void setUp() throws Exception {
        messageQueueProducer = new MessageQueueProducer(messageQueue);
    }

    @Test
    public void givenANewMessageToSend_whenEnqueueMessage_thenMessageIsPersisted()
            throws InterruptedException {
        messageQueueProducer.send(message);

        verify(messageQueue).enqueue(message);
    }
}
