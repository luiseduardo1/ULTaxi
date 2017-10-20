package ca.ulaval.glo4003;

import static java.lang.Thread.sleep;

import ca.ulaval.glo4003.ultaxi.domain.messaging.TaskQueue;
import ca.ulaval.glo4003.ultaxi.domain.messaging.TaskQueueConsumer;
import ca.ulaval.glo4003.ultaxi.domain.messaging.email.exception.EmailSendingFailureException;
import net.jodah.failsafe.Failsafe;
import net.jodah.failsafe.RetryPolicy;
import net.jodah.failsafe.function.CheckedRunnable;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class MessagingServer implements Runnable {

    private Logger logger = Logger.getLogger(MessagingServer.class.getName());
    private TaskQueueConsumer taskQueueConsumer;
    private TaskQueue taskQueue;


    public MessagingServer(TaskQueue taskQueue) {
        this.taskQueue = taskQueue;
        this.taskQueueConsumer = new TaskQueueConsumer(taskQueue);
    }

    RetryPolicy retryPolicy = new RetryPolicy()
            .retryOn(EmailSendingFailureException.class)
            .withDelay(1, TimeUnit.SECONDS)
            .withMaxRetries(3);

    @Override
    public void run() {
        try {
            sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while (true) {
            if (!taskQueue.isEmpty()) {
                CheckedRunnable task = taskQueueConsumer.checkForTask();
                Failsafe.with(retryPolicy).run(task);
                taskQueueConsumer.removeTask(task);
            }
        }
    }
}
