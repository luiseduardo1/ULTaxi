package ca.ulaval.glo4003.ultaxi.domain.messaging;

import net.jodah.failsafe.function.CheckedRunnable;

import java.util.logging.Logger;

public class TaskQueueProducer {

    private TaskQueue taskQueue;
    private Logger logger = Logger.getLogger(TaskQueueProducer.class.getName());

    public TaskQueueProducer(TaskQueue taskQueue) {
        this.taskQueue = taskQueue;
    }

    public void send(CheckedRunnable task) {
        try {
            this.taskQueue.enqueue(task);
        } catch (InterruptedException exception) {
            logger.info(String.format("Not able to enqueue %s task"));
        }
    }
}
