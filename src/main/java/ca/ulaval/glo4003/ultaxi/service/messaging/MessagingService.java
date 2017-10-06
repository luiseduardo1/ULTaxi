package ca.ulaval.glo4003.ultaxi.service.messaging;

import ca.ulaval.glo4003.ultaxi.domain.messaging.Message;
import ca.ulaval.glo4003.ultaxi.domain.messaging.MessageQueueConsumer;
import ca.ulaval.glo4003.ultaxi.domain.messaging.email.Email;
import ca.ulaval.glo4003.ultaxi.domain.messaging.email.exception.EmailSendingFailureException;
import ca.ulaval.glo4003.ultaxi.domain.messaging.email.exception.InvalidEmailTypeException;
import ca.ulaval.glo4003.ultaxi.infrastructure.messaging.EmailSender;

import java.util.logging.Logger;

public class MessagingService {

    private Logger logger = Logger.getLogger(MessagingService.class.getName());
    private MessageQueueConsumer messageQueueConsumer;
    private EmailSender emailSender;

    public MessagingService(MessageQueueConsumer messageQueueConsumer, EmailSender emailSender) {
        this.messageQueueConsumer = messageQueueConsumer;
        this.emailSender = emailSender;
    }

    public void sendMessage() {
        try {
            Message message = this.messageQueueConsumer.checkForMessage();
            Email email = this.messageQueueConsumer.convertToEmail(message);
            this.emailSender.sendEmail(email);
            this.messageQueueConsumer.removeMessage(message);
        } catch (EmailSendingFailureException exception) {
            logger.info("Messaging service not able to send message.");
        } catch (InvalidEmailTypeException exception) {
            logger.info("Messaging service not able to create an email from the message in the queue.");
        }
    }

}
