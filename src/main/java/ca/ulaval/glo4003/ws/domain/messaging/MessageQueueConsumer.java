package ca.ulaval.glo4003.ws.domain.messaging;

import ca.ulaval.glo4003.ws.domain.messaging.email.Email;
import ca.ulaval.glo4003.ws.domain.messaging.email.InvalidEmailTypeException;

public class MessageQueueConsumer {

    private static final String EMAIL_REGISTRATION_SUBJECT = "Hi! Welcome to ULTaxi!";
    private static final String EMAIL_REGISTRATION_CONTENT = "Thank you for your request to " +
        "subscribe to ULTaxi. \nHope you will enjoy it! \n \n \n";
    private static final String EMAIL_SIGNATURE = "Ronald Beaubrun from ULTaxi";

    private MessageQueue messageQueue;

    public MessageQueueConsumer(MessageQueue messageQueue) {
        this.messageQueue = messageQueue;
    }

    public Message checkForMessage() {
        return this.messageQueue.peek();
    }

    public Email convertToEmail(Message message) throws InvalidEmailTypeException {
        switch (message.getReason()) {
            case "Registration":
                Email email = new Email(message.getSentTo(), EMAIL_REGISTRATION_SUBJECT, EMAIL_REGISTRATION_CONTENT,
                    EMAIL_SIGNATURE);
                return email;
            default:
                throw new InvalidEmailTypeException("Invalid type");
        }
    }

    public void removeMessage(Message message) {
        this.messageQueue.dequeue(message);
    }

}