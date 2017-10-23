package ca.ulaval.glo4003.ultaxi.domain.messaging;

import static org.mockito.Mockito.verify;

import ca.ulaval.glo4003.ultaxi.domain.messaging.messagingtask.MessagingTask;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class MessagingTaskProducerImplTest {

    @Mock
    private MessagingTaskQueue messagingTaskQueue;
    @Mock
    private MessagingTask messagingTask;

    private MessagingTaskProducerImpl taskProducer;

    @Before
    public void setUp() throws Exception {
        taskProducer = new MessagingTaskProducerImpl(messagingTaskQueue);
    }

    @Test
    public void givenANewMessageToSend_whenEnqueueMessage_thenMessageIsPersisted()
            throws InterruptedException {
        taskProducer.send(messagingTask);

        verify(messagingTaskQueue).enqueue(messagingTask);
    }
}
