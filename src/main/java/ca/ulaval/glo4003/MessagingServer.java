package ca.ulaval.glo4003;

import ca.ulaval.glo4003.ultaxi.domain.messaging.tasks.Task;
import ca.ulaval.glo4003.ultaxi.domain.messaging.TaskQueue;

public class MessagingServer implements Runnable {

    private TaskQueue taskQueue;

    public MessagingServer(TaskQueue taskQueue) {
        this.taskQueue = taskQueue;
    }

    @Override
    public void run() {
        while (true) {
            if (!taskQueue.isEmpty()) {
                Task task = taskQueue.peek();
                task.execute();
                taskQueue.dequeue(task);
            }
        }
    }
}
