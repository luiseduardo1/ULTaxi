package ca.ulaval.glo4003.ultaxi.infrastructure.messaging;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.BDDMockito.willReturn;

import ca.ulaval.glo4003.ultaxi.domain.messaging.MessagingTaskQueue;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@RunWith(MockitoJUnitRunner.class)
public class MessagingTaskConsumerImplTest {

    @Mock
    private MessagingTaskQueue messagingTaskQueue;

    private MessagingTaskConsumerImpl messagingTaskConsumer;

    @Before
    public void setUp() throws Exception {
        messagingTaskConsumer = new MessagingTaskConsumerImpl(messagingTaskQueue);
    }

    @Test
    public void givenEmptyTaskQueue_whenTaskReceiverIsRunning_thenItShouldNotPeekTask() throws InterruptedException {
        willReturn(true).given(messagingTaskQueue).isEmpty();

        ExecutorService service = Executors.newSingleThreadExecutor();
        service.execute(messagingTaskConsumer);
        service.shutdown();
        service.awaitTermination(1000, TimeUnit.MILLISECONDS);

        verify(messagingTaskQueue, never()).peek();
    }
}
