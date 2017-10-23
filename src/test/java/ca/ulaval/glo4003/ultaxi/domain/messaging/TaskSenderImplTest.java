package ca.ulaval.glo4003.ultaxi.domain.messaging;

import static org.mockito.Mockito.verify;

import ca.ulaval.glo4003.ultaxi.domain.messaging.tasks.Task;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TaskSenderImplTest {

    @Mock
    private TaskQueue taskQueue;
    @Mock
    private Task task;

    private TaskSenderImpl taskSender;

    @Before
    public void setUp() throws Exception {
        taskSender = new TaskSenderImpl(taskQueue);
    }

    @Test
    public void givenANewMessageToSend_whenEnqueueMessage_thenMessageIsPersisted()
            throws InterruptedException {
        taskSender.send(task);

        verify(taskQueue).enqueue(task);
    }
}
