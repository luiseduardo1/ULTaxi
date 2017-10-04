package ca.ulaval.glo4003.ws.domain.messaging;

import java.util.logging.Logger;

public class MessageProducerService {

    private MessageQueue messageQueue;
    private Logger logger = Logger.getLogger(MessageProducerService.class.getName());

    public MessageProducerService(MessageQueue messageQueue) {
        this.messageQueue = messageQueue;
    }

    public void enqueueMessage(Message message) {
        try {
            this.messageQueue.enqueue(message);
        } catch (InterruptedException exception) {
            logger.info("Not able to enqueue registration message.");
        }
    }
}
