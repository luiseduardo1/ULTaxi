package ca.ulaval.glo4003.ultaxi.domain.messaging;

import ca.ulaval.glo4003.ultaxi.domain.messaging.tasks.Task;

import java.util.logging.Logger;

public class TaskProducerImpl implements TaskProducer {

    private TaskQueue taskQueue;
    private Logger logger = Logger.getLogger(TaskProducer.class.getName());

    public TaskProducerImpl(TaskQueue taskQueue) {
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
