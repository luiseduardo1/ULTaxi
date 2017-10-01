package ca.ulaval.glo4003.ws.infrastructure.messaging;

import ca.ulaval.glo4003.ws.domain.messaging.Email;
import ca.ulaval.glo4003.ws.domain.messaging.EmailSendingFailureException;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

public class EmailSender {

    private static final String PROTOCOL = "smtp";
    private Properties emailSenderProperties;
    private Session mailSession;

    public EmailSender(String configurationFilename) {
        emailSenderProperties = System.getProperties();

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream input = classLoader.getResourceAsStream(configurationFilename);
        try {
            emailSenderProperties.load(input);
        } catch (IOException e) {
            System.out.println("Email sender server configuration couldn't be loaded.");
        }
    }

    public void sendEmail(Email email) throws EmailSendingFailureException {
        try {
            MimeMessage emailMessage = createEmailMessage(email);
            Transport transport = mailSession.getTransport(PROTOCOL);
            transport.connect(emailSenderProperties.getProperty("mail.host"), emailSenderProperties.getProperty("mail.from.id"), emailSenderProperties.getProperty("mail.from.password"));
            transport.sendMessage(emailMessage, emailMessage.getAllRecipients());
            transport.close();
        } catch (MessagingException e) {
            throw new EmailSendingFailureException(e);
        } catch (UnsupportedEncodingException e) {
            throw new EmailSendingFailureException(e);
        }
    }

    public MimeMessage createEmailMessage(Email email) throws UnsupportedEncodingException, MessagingException {
        mailSession = Session.getDefaultInstance(emailSenderProperties, null);
        MimeMessage emailMessage = new MimeMessage(mailSession);

        emailMessage.setFrom(new InternetAddress(emailSenderProperties.getProperty("mail.from.address"), emailSenderProperties.getProperty("mail.from.name")));
        InternetAddress[] recipientAddresses = {new InternetAddress(email.getToAddress())};
        emailMessage.setRecipients(Message.RecipientType.TO, recipientAddresses);
        emailMessage.setSubject(email.getSubject());
        emailMessage.setText(email.getContent() + email.getSignature());
        return emailMessage;
    }
}
