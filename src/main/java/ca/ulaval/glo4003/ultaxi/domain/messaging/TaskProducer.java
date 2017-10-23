package ca.ulaval.glo4003.ultaxi.domain.messaging;

import ca.ulaval.glo4003.ultaxi.domain.messaging.tasks.Task;

import java.util.logging.Logger;

public interface TaskProducer {

    void send(Task task);
}
