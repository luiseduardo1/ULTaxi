package ca.ulaval.glo4003.ultaxi.infrastructure.messaging;

import ca.ulaval.glo4003.ultaxi.domain.messaging.MessagingTaskQueue;
import ca.ulaval.glo4003.ultaxi.domain.messaging.messagingtask.MessagingTask;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class MessagingTaskQueueInMemory implements MessagingTaskQueue {

    private final BlockingQueue<MessagingTask> messagingTaskQueue = new LinkedBlockingDeque<MessagingTask>();

    @Override
    public void enqueue(MessagingTask messagingTask) throws InterruptedException {
        this.messagingTaskQueue.put(messagingTask);
    }

    @Override
    public void dequeue(MessagingTask messagingTask) {
        this.messagingTaskQueue.remove(messagingTask);
    }

    @Override
    public MessagingTask peek() {
        return this.messagingTaskQueue.peek();
    }

    @Override
    public boolean isEmpty() {
        return this.messagingTaskQueue.isEmpty();
    }
}

