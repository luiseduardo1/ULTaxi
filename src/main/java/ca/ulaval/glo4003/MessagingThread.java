package ca.ulaval.glo4003;

import ca.ulaval.glo4003.ws.domain.messaging.MessageQueue;
import ca.ulaval.glo4003.ws.domain.messaging.MessageQueueConsumer;
import ca.ulaval.glo4003.ws.infrastructure.messaging.EmailSender;
import ca.ulaval.glo4003.ws.service.messaging.MessagingService;

public class MessagingThread implements Runnable {

    private MessagingService messagingService;
    private MessageQueueConsumer messageQueueConsumer;
    private MessageQueue messageQueue;

    public MessagingThread(MessageQueue messageQueue, EmailSender emailSender) {
        this.messageQueue = messageQueue;
        this.messageQueueConsumer = new MessageQueueConsumer(messageQueue);
        this.messagingService = new MessagingService(messageQueueConsumer, emailSender);
    }

    @Override
    public void run() {
        while (true) {
            if (!messageQueue.isEmpty()) {
                this.messagingService.sendMessage();
            }
        }
    }
}