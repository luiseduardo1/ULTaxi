package ca.ulaval.glo4003.ultaxi.domain.messaging;

import ca.ulaval.glo4003.ultaxi.domain.messaging.tasks.Task;

public interface TaskQueue {

    void enqueue(Task task) throws InterruptedException;

    void dequeue(Task task);

    Task peek();

    boolean isEmpty();
}
