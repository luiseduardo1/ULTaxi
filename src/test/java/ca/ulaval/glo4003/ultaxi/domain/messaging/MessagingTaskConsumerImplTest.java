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
public class MessagingTaskConsumerImplTest {

    @Mock
    private MessagingTaskQueue messagingTaskQueue;

    private MessagingTaskConsumerImpl taskConsumer;

    @Before
    public void setUp() throws Exception {
        taskConsumer = new MessagingTaskConsumerImpl(messagingTaskQueue);
    }

    @Test
    public void givenEmptyTaskQueue_whenTaskReceiverIsRunning_thenItShouldNotPeekTask() {
        willReturn(true).given(messagingTaskQueue).isEmpty();

        ExecutorService service = Executors.newSingleThreadExecutor();
        service.execute(taskConsumer);
        service.shutdown();

        verify(messagingTaskQueue, never()).peek();
    }
}
