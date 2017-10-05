package ca.ulaval.glo4003.ws.domain.messaging;

public interface MessageQueue {

    void enqueue(Message message) throws InterruptedException;

    void dequeue(Message message);

    Message peek();

    boolean isEmpty();
}
