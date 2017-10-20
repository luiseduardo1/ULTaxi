package ca.ulaval.glo4003.ultaxi.domain.messaging;

import net.jodah.failsafe.function.CheckedRunnable;

public interface TaskQueue {

    void enqueue(CheckedRunnable task) throws InterruptedException;

    void dequeue(CheckedRunnable task);

    CheckedRunnable peek();

    boolean isEmpty();
}
