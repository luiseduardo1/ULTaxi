package ca.ulaval.glo4003.ultaxi.domain.messaging.tasks;

import ca.ulaval.glo4003.ultaxi.domain.messaging.email.Email;
import ca.ulaval.glo4003.ultaxi.domain.messaging.email.exception.EmailSendingFailureException;
import ca.ulaval.glo4003.ultaxi.infrastructure.messaging.EmailSender;
import net.jodah.failsafe.function.CheckedRunnable;


public class SendRegistrationEmailTask implements CheckedRunnable {

    private static final String EMAIL_REGISTRATION_SUBJECT = "Welcome %s!!";
    private static final String EMAIL_REGISTRATION_CONTENT = "Thank you %s for your request to " +
            "subscribe to ULTaxi. \nHope you will enjoy it! \n \n \n";
    private static final String EMAIL_SIGNATURE = "Ronald Macdonald from ULTaxi";

    private EmailSender emailSender;
    private String sendTo;
    private String recipientUsername;

    public SendRegistrationEmailTask(String sendTo, String recipientUsername, EmailSender emailSender) {
        this.emailSender = emailSender;
        this.sendTo = sendTo;
        this.recipientUsername = recipientUsername;
    }

    public Email createCustomEmail(String sendTo, String recipientUsername) {
        String customSubject = String.format(EMAIL_REGISTRATION_SUBJECT, recipientUsername);
        String customContent = String.format(EMAIL_REGISTRATION_CONTENT, recipientUsername);
        return new Email(sendTo, customSubject, customContent, EMAIL_SIGNATURE);
    }
    @Override
    public void run() throws EmailSendingFailureException{
        Email email = createCustomEmail(sendTo, recipientUsername);
        this.emailSender.sendEmail(email);
    }
}
