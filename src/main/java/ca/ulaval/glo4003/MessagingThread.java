package ca.ulaval.glo4003;

import ca.ulaval.glo4003.ultaxi.domain.messaging.MessageQueue;
import ca.ulaval.glo4003.ultaxi.domain.messaging.MessageQueueConsumer;
import ca.ulaval.glo4003.ultaxi.infrastructure.messaging.EmailSender;
import ca.ulaval.glo4003.ultaxi.service.messaging.MessagingService;

public class MessagingThread implements Runnable {

    private final MessagingService messagingService;
    private final MessageQueue messageQueue;

    public MessagingThread(MessageQueue messageQueue, EmailSender emailSender) {
        this.messageQueue = messageQueue;
        MessageQueueConsumer messageQueueConsumer = new MessageQueueConsumer(messageQueue);
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