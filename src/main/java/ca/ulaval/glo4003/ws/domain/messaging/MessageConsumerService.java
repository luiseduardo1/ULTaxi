package ca.ulaval.glo4003.ws.domain.messaging;

import ca.ulaval.glo4003.ws.domain.messaging.task.SendQueuedMessageTask;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MessageConsumerService {

    private static final TimeUnit TIME_UNIT = TimeUnit.MILLISECONDS;
    private int INITIAL_DELAY_BEFORE_TASKS = 2000;
    private int TIME_PERIOD_BETWEEN_TASKS = 1000;
    private SendQueuedMessageTask sendQueuedMessageTask;

    public MessageConsumerService(SendQueuedMessageTask sendQueuedMessageTask) {
        this.sendQueuedMessageTask = sendQueuedMessageTask;
    }

    public void sendMessage() {
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleAtFixedRate(this.sendQueuedMessageTask, INITIAL_DELAY_BEFORE_TASKS, TIME_PERIOD_BETWEEN_TASKS, TIME_UNIT);
    }
}