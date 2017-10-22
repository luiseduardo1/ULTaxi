package ca.ulaval.glo4003.ultaxi.infrastructure.messaging;

import ca.ulaval.glo4003.ultaxi.domain.messaging.TaskQueue;
import ca.ulaval.glo4003.ultaxi.domain.messaging.tasks.Task;
import net.jodah.failsafe.function.CheckedRunnable;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class TaskQueueInMemory implements TaskQueue {

    private BlockingQueue<Task> taskQueue = new LinkedBlockingDeque<Task>();

    @Override
    public void enqueue(Task task) throws InterruptedException {
        this.taskQueue.put(task);
    }

    @Override
    public void dequeue(Task task) {
        this.taskQueue.remove(task);
    }

    @Override
    public Task peek() {
        return this.taskQueue.peek();
    }

    @Override
    public boolean isEmpty() {
        return this.taskQueue.isEmpty();
    }
}

