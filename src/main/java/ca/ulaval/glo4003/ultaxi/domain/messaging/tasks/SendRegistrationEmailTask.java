package ca.ulaval.glo4003.ultaxi.domain.messaging.tasks;

import ca.ulaval.glo4003.ultaxi.domain.messaging.email.Email;
import ca.ulaval.glo4003.ultaxi.domain.messaging.email.exception.EmailSendingFailureException;
import ca.ulaval.glo4003.ultaxi.infrastructure.messaging.EmailSender;
import net.jodah.failsafe.Failsafe;
import net.jodah.failsafe.RetryPolicy;
import java.util.concurrent.TimeUnit;

public class SendRegistrationEmailTask extends Task {

    private static final String EMAIL_REGISTRATION_SUBJECT = "Welcome %s!";
    private static final String EMAIL_REGISTRATION_CONTENT = "Thank you %s for your request to " +
            "subscribe to ULTaxi. \nHope you will enjoy it! \n \n \n";
    private static final String EMAIL_SIGNATURE = "Ronald Macdonald from ULTaxi";
    private int DELAY_SECONDS_BETWEEN_RETRY_ATTEMPT = 10;

    private EmailSender emailSender;
    private String sendTo;
    private String recipientUsername;
    private RetryPolicy retryPolicy;

    public SendRegistrationEmailTask(String sendTo, String recipientUsername, EmailSender emailSender) {
        this.emailSender = emailSender;
        this.sendTo = sendTo;
        this.recipientUsername = recipientUsername;
        this.retryPolicy = new RetryPolicy().retryOn(EmailSendingFailureException.class)
                .withDelay(DELAY_SECONDS_BETWEEN_RETRY_ATTEMPT, TimeUnit.SECONDS);
    }

    @Override
    public void run() {
        Failsafe.with(retryPolicy).run(this::sendRegistrationEmail);
    }

    public void sendRegistrationEmail() {
        String customSubject = String.format(EMAIL_REGISTRATION_SUBJECT, recipientUsername);
        String customContent = String.format(EMAIL_REGISTRATION_CONTENT, recipientUsername);
        Email registrationEmail = new Email(sendTo, customSubject, customContent, EMAIL_SIGNATURE);
        emailSender.sendEmail(registrationEmail);
    }

}
