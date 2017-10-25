package ca.ulaval.glo4003.ultaxi.infrastructure.messaging;

import ca.ulaval.glo4003.ultaxi.domain.messaging.MessagingTaskConsumer;
import ca.ulaval.glo4003.ultaxi.domain.messaging.MessagingTaskQueue;
import ca.ulaval.glo4003.ultaxi.domain.messaging.messagingtask.MessagingTask;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MessagingTaskConsumerImpl implements MessagingTaskConsumer {

    private static int threadsNumber = 10;
    private final MessagingTaskQueue messagingTaskQueue;
    private final ExecutorService threadPool;
    private boolean isRunning = true;

    public MessagingTaskConsumerImpl(MessagingTaskQueue messagingTaskQueue) {
        this.messagingTaskQueue = messagingTaskQueue;
        this.threadPool = Executors.newFixedThreadPool(threadsNumber);
    }

    @Override
    public void run() {
        while (isRunning) {
            if (!messagingTaskQueue.isEmpty()) {
                MessagingTask messagingTask = messagingTaskQueue.peek();
                threadPool.execute(messagingTask);
                messagingTaskQueue.dequeue(messagingTask);
            }
        }
    }

    public void stopTaskMessagingConsumerThread() {
        this.isRunning = false;
    }

}
