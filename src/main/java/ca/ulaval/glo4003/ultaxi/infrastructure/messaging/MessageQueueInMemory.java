package ca.ulaval.glo4003.ultaxi.infrastructure.messaging;

import ca.ulaval.glo4003.ultaxi.domain.messaging.Message;
import ca.ulaval.glo4003.ultaxi.domain.messaging.MessageQueue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class MessageQueueInMemory implements MessageQueue {

    private final BlockingQueue<Message> messageQueue = new LinkedBlockingDeque<>();

    @Override
    public void enqueue(Message message) throws InterruptedException {
        this.messageQueue.put(message);
    }

    @Override
    public void dequeue(Message message) {
        this.messageQueue.remove(message);
    }

    @Override
    public Message peek() {
        return this.messageQueue.peek();
    }

    @Override
    public boolean isEmpty() {
        return this.messageQueue.isEmpty();
    }
}
