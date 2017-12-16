package ca.ulaval.glo4003.ultaxi.infrastructure.messaging;

import ca.ulaval.glo4003.ultaxi.domain.messaging.MessagingTaskQueue;
import ca.ulaval.glo4003.ultaxi.domain.messaging.messagingtask.MessagingTask;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class MessagingTaskProducerImplTest {

    @Mock
    private MessagingTaskQueue messagingTaskQueue;
    @Mock
    private MessagingTask messagingTask;

    private MessagingTaskProducerImpl messagingTaskProducer;

    @Before
    public void setUp() throws Exception {
        messagingTaskProducer = new MessagingTaskProducerImpl(messagingTaskQueue);
    }

    @Test
    public void givenANewMessageToSend_whenEnqueueMessage_thenMessageIsPersisted()
        throws InterruptedException {
        messagingTaskProducer.send(messagingTask);

        verify(messagingTaskQueue).enqueue(messagingTask);
    }
}
