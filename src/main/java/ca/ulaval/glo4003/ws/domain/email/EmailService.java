package ca.ulaval.glo4003.ws.domain.email;

import ca.ulaval.glo4003.ws.infrastructure.email.EmailSender;

import java.util.logging.Logger;

public class EmailService {

    private static final String EMAIL_SUBJECT = "Hi! Welcome to ULTaxi!";
    private static final String EMAIL_CONTENT = "Thank you for your request to subscribe to ULTaxi. \nHope you enjoy it! \n \n \n";
    private static final String EMAIL_SIGNATURE = "Ronald Beaubrun from ULTaxi";

    private Logger logger = Logger.getLogger(EmailService.class.getName());
    private EmailSender emailSender;

    public EmailService(EmailSender emailSender) {
        this.emailSender = emailSender;
    }

    public boolean sendRegistrationEmail(String userAddress) {
        logger.info(String.format("EmailService: Send registration email to %s", userAddress));
        Email registrationEmail = new Email(userAddress, EMAIL_SUBJECT, EMAIL_CONTENT, EMAIL_SIGNATURE);

        if (this.emailSender.sendEmail(registrationEmail)) {
            logger.info("Registration email successfully sended.");
            return true;
        } else {
            logger.info("Registration email couldn't be sended.");
            return false;
        }
    }
}
