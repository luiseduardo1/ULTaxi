package ca.ulaval.glo4003.ultaxi.infrastructure.messaging;

import static org.junit.Assert.assertEquals;

import ca.ulaval.glo4003.ultaxi.domain.messaging.TaskQueue;
import ca.ulaval.glo4003.ultaxi.domain.messaging.tasks.Task;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TaskQueueInMemoryTest {

    @Mock
    private Task task;

    private TaskQueue taskQueue;

    @Before
    public void setUp() {
        taskQueue = new TaskQueueInMemory();

    }

    @Test
    public void givenATask_whenTaskIsEnqueued_thenTaskHasSameParameters()
            throws InterruptedException {
        taskQueue.enqueue(task);

        Task taskDequeued = taskQueue.peek();

        assertEquals(task, taskDequeued);
    }
}

