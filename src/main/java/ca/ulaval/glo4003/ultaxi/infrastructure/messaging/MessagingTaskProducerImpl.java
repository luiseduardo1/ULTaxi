package ca.ulaval.glo4003.ultaxi.infrastructure.messaging;

import ca.ulaval.glo4003.ultaxi.domain.messaging.MessagingTaskProducer;
import ca.ulaval.glo4003.ultaxi.domain.messaging.MessagingTaskQueue;
import ca.ulaval.glo4003.ultaxi.domain.messaging.messagingtask.MessagingTask;

import java.util.logging.Logger;

public class MessagingTaskProducerImpl implements MessagingTaskProducer {

    private final MessagingTaskQueue messagingTaskQueue;
    private final Logger logger = Logger.getLogger(MessagingTaskProducer.class.getName());

    public MessagingTaskProducerImpl(MessagingTaskQueue messagingTaskQueue) {
        this.messagingTaskQueue = messagingTaskQueue;
    }

    public void send(MessagingTask messagingTask) {
        try {
            this.messagingTaskQueue.enqueue(messagingTask);
        } catch (InterruptedException exception) {
            logger.info("Not able to enqueue the messagingTask");
        }
    }
}
