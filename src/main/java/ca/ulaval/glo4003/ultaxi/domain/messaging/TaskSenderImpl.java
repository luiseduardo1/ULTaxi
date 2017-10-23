package ca.ulaval.glo4003.ultaxi.domain.messaging;

import ca.ulaval.glo4003.ultaxi.domain.messaging.tasks.Task;

import java.util.logging.Logger;

public class TaskSenderImpl implements TaskSender {

    private TaskQueue taskQueue;
    private Logger logger = Logger.getLogger(TaskSender.class.getName());

    public TaskSenderImpl(TaskQueue taskQueue) {
        this.taskQueue = taskQueue;
    }

    public void send(Task task) {
        try {
            this.taskQueue.enqueue(task);
        } catch (InterruptedException exception) {
            logger.info(String.format("Not able to enqueue task"));
        }
    }
}
