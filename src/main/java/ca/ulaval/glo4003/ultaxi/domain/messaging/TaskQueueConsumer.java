package ca.ulaval.glo4003.ultaxi.domain.messaging;

import net.jodah.failsafe.function.CheckedRunnable;

public class TaskQueueConsumer {

    private TaskQueue taskQueue;

    public TaskQueueConsumer(TaskQueue taskQueue) {
        this.taskQueue = taskQueue;
    }

    public CheckedRunnable checkForTask() {
        return this.taskQueue.peek();
    }

    public void removeTask(CheckedRunnable task) {
        this.taskQueue.dequeue(task);
    }

}
