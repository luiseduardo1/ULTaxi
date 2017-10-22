package ca.ulaval.glo4003;

import ca.ulaval.glo4003.ultaxi.domain.messaging.TaskQueue;
import ca.ulaval.glo4003.ultaxi.domain.messaging.TaskQueueConsumer;
import ca.ulaval.glo4003.ultaxi.domain.messaging.tasks.Task;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MessagingServer implements Runnable {

    private static int threadsNumber = 10;
    private TaskQueueConsumer taskQueueConsumer;
    private TaskQueue taskQueue;
    private ExecutorService threadPool;

    public MessagingServer(TaskQueue taskQueue) {
        this.taskQueue = taskQueue;
        this.taskQueueConsumer = new TaskQueueConsumer(taskQueue);
        this.threadPool = Executors.newFixedThreadPool(threadsNumber);
    }

    @Override
    public void run() {
        while (true) {
            if (!taskQueue.isEmpty()) {
                Task task = taskQueueConsumer.checkForTask();
                threadPool.execute(task);
                taskQueueConsumer.removeTask(task);
            }
        }
    }
}
