package ca.ulaval.glo4003.ultaxi.infrastructure.messaging;

import ca.ulaval.glo4003.ultaxi.domain.messaging.TaskQueue;
import ca.ulaval.glo4003.ultaxi.domain.messaging.tasks.Task;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class TaskQueueInMemory implements TaskQueue {

    private BlockingQueue<Runnable> taskQueue = new LinkedBlockingDeque<Runnable>();

    @Override
    public void enqueue(Runnable task) throws InterruptedException {
        this.taskQueue.put(task);
    }

    @Override
    public void dequeue(Runnable task) {
        this.taskQueue.remove(task);
    }

    @Override
    public Runnable peek() {
        return this.taskQueue.peek();
    }

    @Override
    public boolean isEmpty() {
        return this.taskQueue.isEmpty();
    }
}

