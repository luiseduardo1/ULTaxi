package ca.ulaval.glo4003.ultaxi.domain.messaging;

import java.util.logging.Logger;

public class MessageQueueProducer {

    private MessageQueue messageQueue;
    private Logger logger = Logger.getLogger(MessageQueueProducer.class.getName());

    public MessageQueueProducer(MessageQueue messageQueue) {
        this.messageQueue = messageQueue;
    }

    public void send(Message message) {
        try {
            this.messageQueue.enqueue(message);
        } catch (InterruptedException exception) {
            logger.info(String.format("Not able to enqueue %s message.", message.getReason()));
        }
    }
}
