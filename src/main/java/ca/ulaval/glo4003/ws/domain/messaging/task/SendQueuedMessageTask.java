package ca.ulaval.glo4003.ws.domain.messaging.task;

import ca.ulaval.glo4003.ws.domain.messaging.Email;
import ca.ulaval.glo4003.ws.domain.messaging.EmailSendingFailureException;
import ca.ulaval.glo4003.ws.domain.messaging.Message;
import ca.ulaval.glo4003.ws.domain.messaging.MessageQueue;
import ca.ulaval.glo4003.ws.infrastructure.messaging.EmailSender;

import java.util.logging.Logger;

public class SendQueuedMessageTask implements Runnable {

    private static final String EMAIL_REGISTRATION_SUBJECT = "Hi! Welcome to ULTaxi!";
    private static final String EMAIL_REGISTRATION_CONTENT = "Thank you for your request to " +
        "subscribe to ULTaxi. \nHope you enjoy it! \n \n \n";
    private static final String EMAIL_SIGNATURE = "Ronald Beaubrun from ULTaxi";
    private Logger logger = Logger.getLogger(SendQueuedMessageTask.class.getName());
    private MessageQueue messageQueue;
    private EmailSender emailSender;

    public SendQueuedMessageTask(MessageQueue messageQueue, EmailSender emailSender) {
        this.messageQueue = messageQueue;
        this.emailSender = emailSender;
    }

    @Override
    public void run() {
        sendMessage();
    }

    public void sendMessage() {
        if (!this.messageQueue.isEmpty()) {
            Message message = this.messageQueue.peek();
            Email email = createEmailFromMessage(message);
            try {
                this.emailSender.sendEmail(email);
                this.messageQueue.dequeue(message);
            } catch (EmailSendingFailureException exception) {
                logger.info(String.format("Unable to send message to %s", email.getToAddress()));
            }
        }
    }

    private Email createEmailFromMessage(Message message) {
        Email email = null;
        if (message.getReason().equals("Registration")) {
            email = new Email(message.getSentTo(), EMAIL_REGISTRATION_SUBJECT,
                              EMAIL_REGISTRATION_CONTENT,
                              EMAIL_SIGNATURE);
            return email;
        }
        return email;
    }
}