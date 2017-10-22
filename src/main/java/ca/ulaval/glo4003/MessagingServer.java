package ca.ulaval.glo4003;

import ca.ulaval.glo4003.ultaxi.domain.messaging.TaskQueue;
import ca.ulaval.glo4003.ultaxi.domain.messaging.TaskReceiver;
import ca.ulaval.glo4003.ultaxi.domain.messaging.tasks.Task;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MessagingServer implements Runnable {

    private static int threadsNumber = 10;
    private final TaskReceiver taskReceiver;
    private final TaskQueue taskQueue;
    private final ExecutorService threadPool;

    public MessagingServer(TaskQueue taskQueue) {
        this.taskQueue = taskQueue;
        this.taskReceiver = new TaskReceiver(taskQueue);
        this.threadPool = Executors.newFixedThreadPool(threadsNumber);
    }

    @Override
    public void run() {
        while (true) {
            if (!taskQueue.isEmpty()) {
                Task task = taskReceiver.checkForTask();
                threadPool.execute(task);
                taskReceiver.removeTask(task);
            }
        }
    }
}
