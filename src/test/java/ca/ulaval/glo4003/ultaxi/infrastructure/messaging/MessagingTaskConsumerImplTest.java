package ca.ulaval.glo4003.ultaxi.infrastructure.messaging;

import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import ca.ulaval.glo4003.ultaxi.domain.messaging.MessagingTaskQueue;
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

    private MessagingTaskConsumerImpl messagingTaskConsumer;

    @Before
    public void setUp() throws Exception {
        messagingTaskConsumer = new MessagingTaskConsumerImpl(messagingTaskQueue);
    }

    @Test
    public void givenAnEmptyMessagingTaskQueue_whenRunning_thenPeekIsNeverCalled() throws InterruptedException {
        willReturn(true).given(messagingTaskQueue).isEmpty();

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(messagingTaskConsumer);
        executorService.shutdown();
        messagingTaskConsumer.stopMessagingThreadConsumer();

        verify(messagingTaskQueue, never()).peek();
    }

}

