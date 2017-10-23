package ca.ulaval.glo4003.ultaxi.domain.messaging;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.BDDMockito.willReturn;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RunWith(MockitoJUnitRunner.class)
public class TaskReceiverImplTest {

    @Mock
    private TaskQueue taskQueue;

    private TaskReceiverImpl taskReceiver;

    @Before
    public void setUp() throws Exception {
        taskReceiver = new TaskReceiverImpl(taskQueue);
    }

    @Test
    public void givenEmptyTaskQueue_whenTaskReceiverIsRunning_thenItShouldNotPeekTask() {
        willReturn(true).given(taskQueue).isEmpty();

        ExecutorService service = Executors.newSingleThreadExecutor();
        service.execute(taskReceiver);
        service.shutdown();

        verify(taskQueue, never()).peek();
    }
}
