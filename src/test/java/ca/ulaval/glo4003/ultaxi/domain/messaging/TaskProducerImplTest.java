package ca.ulaval.glo4003.ultaxi.domain.messaging;

import static org.mockito.Mockito.verify;

import ca.ulaval.glo4003.ultaxi.domain.messaging.tasks.Task;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TaskProducerImplTest {

    @Mock
    private TaskQueue taskQueue;
    @Mock
    private Task task;

    private TaskProducerImpl taskProducer;

    @Before
    public void setUp() throws Exception {
        taskProducer = new TaskProducerImpl(taskQueue);
    }

    @Test
    public void givenANewMessageToSend_whenEnqueueMessage_thenMessageIsPersisted()
            throws InterruptedException {
        taskProducer.send(task);

        verify(taskQueue).enqueue(task);
    }
}
