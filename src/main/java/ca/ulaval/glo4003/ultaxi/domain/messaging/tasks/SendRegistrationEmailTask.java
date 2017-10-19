package ca.ulaval.glo4003.ultaxi.domain.messaging.tasks;

import ca.ulaval.glo4003.ultaxi.domain.messaging.email.Email;
import ca.ulaval.glo4003.ultaxi.domain.messaging.email.exception.EmailSendingFailureException;
import ca.ulaval.glo4003.ultaxi.infrastructure.messaging.EmailSender;

public class SendRegistrationEmailTask implements Task {

    private static final String EMAIL_REGISTRATION_SUBJECT = "Hi! Welcome to ULTaxi!";
    private static final String EMAIL_REGISTRATION_CONTENT = "Thank you for your request to " +
            "subscribe to ULTaxi. \nHope you will enjoy it! \n " +
            "\n \n";
    private static final String EMAIL_SIGNATURE = "Ronald Macdonald from ULTaxi";

    private String sendTo;
    private EmailSender emailSender;

    public SendRegistrationEmailTask(String sendTo, EmailSender emailSender) {
        this.sendTo = sendTo;
        this.emailSender = emailSender;
    }

    @Override
    public void execute() throws EmailSendingFailureException {
        Email email = new Email(sendTo, EMAIL_REGISTRATION_SUBJECT, EMAIL_REGISTRATION_CONTENT,
                EMAIL_SIGNATURE);
        this.emailSender.sendEmail(email);
    }

}
