package ca.ulaval.glo4003;

import ca.ulaval.glo4003.ws.domain.messaging.MessageConsumerService;
import ca.ulaval.glo4003.ws.domain.messaging.MessageQueue;
import ca.ulaval.glo4003.ws.domain.messaging.task.SendQueuedMessageTask;
import ca.ulaval.glo4003.ws.infrastructure.messaging.EmailSender;

public class MessagingThread implements Runnable {

    private MessageConsumerService messageConsumerService;
    private SendQueuedMessageTask sendQueuedMessageTask;

    public MessagingThread(MessageQueue messageQueue, EmailSender emailSender) {
        this.sendQueuedMessageTask = new SendQueuedMessageTask(messageQueue, emailSender);
        this.messageConsumerService = new MessageConsumerService(this.sendQueuedMessageTask);
    }

    @Override
    public void run() {
        this.messageConsumerService.sendMessage();
    }
}