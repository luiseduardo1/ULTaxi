package ca.ulaval.glo4003;

import static java.lang.Thread.sleep;

import ca.ulaval.glo4003.ultaxi.domain.messaging.TaskQueue;
import ca.ulaval.glo4003.ultaxi.domain.messaging.TaskQueueConsumer;
import ca.ulaval.glo4003.ultaxi.domain.messaging.email.exception.EmailSendingFailureException;
import ca.ulaval.glo4003.ultaxi.domain.messaging.tasks.Task;

import java.util.logging.Logger;

public class MessagingServer implements Runnable {

    private Logger logger = Logger.getLogger(MessagingServer.class.getName());
    private TaskQueueConsumer taskQueueConsumer;
    private TaskQueue taskQueue;


    public MessagingServer(TaskQueue taskQueue) {
        this.taskQueue = taskQueue;
        this.taskQueueConsumer = new TaskQueueConsumer(taskQueue);
    }

    @Override
    public void run() {
        try {
            sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while (true) {
            if (!taskQueue.isEmpty()) {
                Runnable task = taskQueueConsumer.checkForTask();
                task.run();
                taskQueueConsumer.removeTask(task);
            }
        }
    }
}
