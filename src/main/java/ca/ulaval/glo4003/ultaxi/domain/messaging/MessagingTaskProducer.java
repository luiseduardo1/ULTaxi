package ca.ulaval.glo4003.ultaxi.domain.messaging;

import ca.ulaval.glo4003.ultaxi.domain.messaging.messagingtask.MessagingTask;

public interface MessagingTaskProducer {

    void send(MessagingTask messagingTask);
}
