package ca.ulaval.glo4003.ultaxi.domain.messaging;

public interface TaskQueue {

    void enqueue(Runnable task) throws InterruptedException;

    void dequeue(Runnable task);

    Runnable peek();

    boolean isEmpty();
}
