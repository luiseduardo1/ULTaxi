package ca.ulaval.glo4003.ultaxi.domain.messaging;

import static org.mockito.Mockito.verify;

import net.jodah.failsafe.function.CheckedRunnable;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class TaskQueueConsumerTest {

    private static final String SEND_TO = "test@example.com";
    private static final String REASON = "Registration";

    TaskQueueConsumer taskQueueConsumer;

    @Mock
    TaskQueue taskQueue;

    private Runnable task;


    @Before
    public void setUp() throws Exception {
        taskQueueConsumer = new TaskQueueConsumer(taskQueue);
    }

    @Test
    public void givenATaskInQueue_whenCheckForTask_thenPeekFunctionIsCalledOnQueue() {
        taskQueueConsumer.checkForTask();

        verify(taskQueue).peek();
    }

    @Test
    public void givenATaskInQueue_whenRemoveTask_theDequeueFunctionIsCalledOnQueue() {
        task = taskQueueConsumer.checkForTask();

        taskQueueConsumer.removeTask(task);

        verify(taskQueue).dequeue(task);
    }

}
