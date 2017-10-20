package ca.ulaval.glo4003.ultaxi.infrastructure.messaging;

import ca.ulaval.glo4003.ultaxi.domain.messaging.TaskQueue;
import net.jodah.failsafe.function.CheckedRunnable;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class TaskQueueInMemory implements TaskQueue {

    private BlockingQueue<CheckedRunnable> taskQueue = new LinkedBlockingDeque<CheckedRunnable>();

    @Override
    public void enqueue(CheckedRunnable task) throws InterruptedException {
        this.taskQueue.put(task);
    }

    @Override
    public void dequeue(CheckedRunnable task) {
        this.taskQueue.remove(task);
    }

    @Override
    public CheckedRunnable peek() {
        return this.taskQueue.peek();
    }

    @Override
    public boolean isEmpty() {
        return this.taskQueue.isEmpty();
    }
}

