package ca.ulaval.glo4003.ultaxi.infrastructure.messaging;

import ca.ulaval.glo4003.ultaxi.domain.messaging.email.Email;
import ca.ulaval.glo4003.ultaxi.domain.messaging.email.exception.EmailSendingFailureException;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

public class EmailSender {

    private static final String PROTOCOL = "smtp";
    private final Properties emailSenderProperties;
    private Session mailSession;

    public EmailSender(EmailSenderConfigurationReader configurationReader) {
        emailSenderProperties = configurationReader.read();
    }

    public void sendEmail(Email email) throws EmailSendingFailureException {
        try {
            MimeMessage emailMessage = createEmailMessage(email);
            Transport transport = mailSession.getTransport(PROTOCOL);
            transport.connect(emailSenderProperties.getProperty("mail.host"),
                              emailSenderProperties.getProperty("mail.from.id"),
                              emailSenderProperties.getProperty("mail.from.password"));
            transport.sendMessage(emailMessage, emailMessage.getAllRecipients());
            transport.close();
        } catch (MessagingException | UnsupportedEncodingException exception) {
            throw new EmailSendingFailureException(exception);
        }
    }

    private MimeMessage createEmailMessage(Email email)
        throws UnsupportedEncodingException, MessagingException {
        mailSession = Session.getDefaultInstance(emailSenderProperties, null);
        MimeMessage emailMessage = new MimeMessage(mailSession);

        emailMessage.setFrom(new InternetAddress(emailSenderProperties.getProperty("mail.from.address"),
                                                 emailSenderProperties.getProperty("mail.from.name")));
        InternetAddress[] recipientAddresses = {new InternetAddress(email.getToAddress())};
        emailMessage.setRecipients(Message.RecipientType.TO, recipientAddresses);
        emailMessage.setSubject(email.getSubject());
        emailMessage.setText(email.getContent() + email.getSignature());
        return emailMessage;
    }
}
