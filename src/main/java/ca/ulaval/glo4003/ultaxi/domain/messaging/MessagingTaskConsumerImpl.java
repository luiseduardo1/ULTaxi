package ca.ulaval.glo4003.ultaxi.domain.messaging;

import ca.ulaval.glo4003.ultaxi.domain.messaging.messagingtask.MessagingTask;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MessagingTaskConsumerImpl implements MessagingTaskConsumer {

    private static int threadsNumber = 10;
    private final MessagingTaskQueue messagingTaskQueue;
    private final ExecutorService threadPool;

    public MessagingTaskConsumerImpl(MessagingTaskQueue messagingTaskQueue) {
        this.messagingTaskQueue = messagingTaskQueue;
        this.threadPool = Executors.newFixedThreadPool(threadsNumber);
    }

    @Override
    public void run() {
        while (true) {
            if (!messagingTaskQueue.isEmpty()) {
                MessagingTask messagingTask = messagingTaskQueue.peek();
                threadPool.execute(messagingTask);
                messagingTaskQueue.dequeue(messagingTask);
            }
        }
    }
}
