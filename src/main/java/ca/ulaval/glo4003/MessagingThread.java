package ca.ulaval.glo4003;

import ca.ulaval.glo4003.ws.domain.messaging.MessageConsumerService;
import ca.ulaval.glo4003.ws.domain.messaging.MessageQueue;
import ca.ulaval.glo4003.ws.infrastructure.messaging.EmailSender;


public class MessagingThread implements Runnable {

    private EmailSender emailSender;
    private MessageConsumerService messageConsumerService;

    public MessagingThread(MessageQueue messageQueue, EmailSender emailSender) {
        this.emailSender = emailSender;
        this.messageConsumerService = new MessageConsumerService(messageQueue, emailSender);
    }

    @Override
    public void run() {
        while (true) {
            this.messageConsumerService.sendMessage();
        }
    }
}