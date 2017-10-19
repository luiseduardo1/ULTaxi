package ca.ulaval.glo4003.ultaxi.domain.messaging;

import ca.ulaval.glo4003.ultaxi.domain.messaging.tasks.Task;

public interface TaskQueue {

    void enqueue(Runnable task) throws InterruptedException;

    void dequeue(Runnable task);

    Runnable peek();

    boolean isEmpty();
}
