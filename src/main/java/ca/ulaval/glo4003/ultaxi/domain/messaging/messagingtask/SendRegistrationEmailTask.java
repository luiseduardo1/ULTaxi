package ca.ulaval.glo4003.ultaxi.domain.messaging.messagingtask;

import ca.ulaval.glo4003.ultaxi.domain.messaging.email.Email;
import ca.ulaval.glo4003.ultaxi.domain.messaging.email.EmailSender;
import ca.ulaval.glo4003.ultaxi.domain.messaging.email.exception.EmailSendingFailureException;
import ca.ulaval.glo4003.ultaxi.utils.StringUtil;
import net.jodah.failsafe.Failsafe;
import net.jodah.failsafe.RetryPolicy;

import java.util.concurrent.TimeUnit;

public class SendRegistrationEmailTask implements MessagingTask {

    private static final String EMAIL_REGISTRATION_SUBJECT = "Hi %s! Welcome to ULTaxi! ";
    private static final String EMAIL_REGISTRATION_CONTENT = "Thank you %s for your request to " +
        "subscribe to ULTaxi. \nHope you will enjoy it! \n \n \n";
    private static final String EMAIL_SIGNATURE = "Ronald Macdonald from ULTaxi";
    private int DELAY_SECONDS_BETWEEN_RETRY_ATTEMPT = 60;

    private EmailSender emailSender;
    private String sendTo;
    private String recipientUsername;
    private RetryPolicy retryPolicy;

    public SendRegistrationEmailTask(String sendTo, String recipientUsername, EmailSender emailSender) {
        this.sendTo = sendTo;
        this.recipientUsername = StringUtil.capitalize(recipientUsername);
        this.emailSender = emailSender;
    }

    @Override
    public void run() {
        setRetryPolicy();
        Failsafe.with(retryPolicy).run(this::execute);
    }

    private void setRetryPolicy() {
        this.retryPolicy = new RetryPolicy().retryOn(EmailSendingFailureException.class)
            .withDelay(DELAY_SECONDS_BETWEEN_RETRY_ATTEMPT, TimeUnit.SECONDS);
    }

    public void execute() {
        String customSubject = String.format(EMAIL_REGISTRATION_SUBJECT, recipientUsername);
        String customContent = String.format(EMAIL_REGISTRATION_CONTENT, recipientUsername);
        Email registrationEmail = new Email(sendTo, customSubject, customContent, EMAIL_SIGNATURE);
        emailSender.sendEmail(registrationEmail);
    }

}
