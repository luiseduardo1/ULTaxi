package ca.ulaval.glo4003.ultaxi.domain.messaging;

import ca.ulaval.glo4003.ultaxi.domain.messaging.tasks.Task;

public class TaskReceiver {

    private TaskQueue taskQueue;

    public TaskReceiver(TaskQueue taskQueue) {
        this.taskQueue = taskQueue;
    }

    public Task checkForTask() {
        return this.taskQueue.peek();
    }

    public void removeTask(Task task) {
        this.taskQueue.dequeue(task);
    }

}
