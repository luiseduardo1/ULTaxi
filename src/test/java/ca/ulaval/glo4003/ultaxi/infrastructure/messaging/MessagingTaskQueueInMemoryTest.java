package ca.ulaval.glo4003.ultaxi.infrastructure.messaging;

import static org.junit.Assert.assertEquals;

import ca.ulaval.glo4003.ultaxi.domain.messaging.MessagingTaskQueue;
import ca.ulaval.glo4003.ultaxi.domain.messaging.messagingtask.MessagingTask;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class MessagingTaskQueueInMemoryTest {

    @Mock
    private MessagingTask messagingTask;

    private MessagingTaskQueue messagingTaskQueue;

    @Before
    public void setUp() {
        messagingTaskQueue = new MessagingTaskQueueInMemory();
    }

    @Test
    public void givenATask_whenTaskIsEnqueued_thenTaskHasSameParameters()
            throws InterruptedException {
        messagingTaskQueue.enqueue(messagingTask);

        MessagingTask messagingTaskDequeued = messagingTaskQueue.peek();

        assertEquals(messagingTask, messagingTaskDequeued);
    }
}

