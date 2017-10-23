package ca.ulaval.glo4003.ultaxi.domain.messaging;

import ca.ulaval.glo4003.ultaxi.domain.messaging.tasks.Task;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TaskReceiver implements Runnable{

    private static int threadsNumber = 10;
    private final TaskQueue taskQueue;
    private final ExecutorService threadPool;

    public TaskReceiver(TaskQueue taskQueue) {
        this.taskQueue = taskQueue;
        this.threadPool = Executors.newFixedThreadPool(threadsNumber);
    }

    @Override
    public void run() {
        while (true) {
            if (!taskQueue.isEmpty()) {
                Task task = taskQueue.peek();
                threadPool.execute(task);
                taskQueue.dequeue(task);
            }
        }
    }


}
