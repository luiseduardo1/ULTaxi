package ca.ulaval.glo4003.ultaxi.domain.messaging;

import ca.ulaval.glo4003.ultaxi.domain.messaging.messagingtask.MessagingTask;

public interface MessagingTaskQueue {

    void enqueue(MessagingTask messagingTask) throws InterruptedException;

    void dequeue(MessagingTask messagingTask);

    MessagingTask peek();

    boolean isEmpty();
}
