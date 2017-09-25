package ca.ulaval.glo4003.ws.infrastructure.email;

import ca.ulaval.glo4003.ws.domain.email.Email;
import ca.ulaval.glo4003.ws.domain.email.EmailSendingFailureException;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Logger;

public class EmailSender {

    private static final String EMAIL_SENDER_PROPERTIES_FILENAME = "emailConfigurations.properties";
    private Properties emailSenderProperties = new Properties();
    private Logger logger = Logger.getLogger(EmailSender.class.getName());

    public EmailSender() {
        setProperties(EMAIL_SENDER_PROPERTIES_FILENAME);
    }

    private void setProperties(String propertiesFilename) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream input = classLoader.getResourceAsStream(propertiesFilename);
        try {
            emailSenderProperties.load(input);
        } catch (IOException e) {
            logger.info("Email sender properties couldn't be loaded.");
        }
    }

    public void sendEmail(Email email) throws EmailSendingFailureException {
        try {
            System.out.println("Sending email...");
            Session mailSession = createNewMailSession();

            Message msg = new MimeMessage(mailSession);
            msg.setFrom(new InternetAddress(emailSenderProperties.getProperty("user_email"), emailSenderProperties.getProperty("user_name")));
            InternetAddress[] recipientAddresses = {new InternetAddress(email.getRecipientAddress())};
            msg.setRecipients(Message.RecipientType.TO, recipientAddresses);
            msg.setSubject(email.getSubject());
            msg.setSentDate(new Date());
            msg.setText(email.getContent() + email.getSignature());

            Transport.send(msg);
        } catch (MessagingException e) {
            logger.info("Email sending failed.");
            throw new EmailSendingFailureException(e);
        } catch (UnsupportedEncodingException e) {
            logger.info("Email sending failed.");
            throw new EmailSendingFailureException(e);
        }
    }

    private Session createNewMailSession() {
        Properties smtpServerProperties = setServerProperties();
        Authenticator auth = createNewSessionAuthenticator();
        Session mailSession = Session.getInstance(smtpServerProperties, auth);
        return mailSession;
    }

    private Properties setServerProperties() {
        Properties smtpServerProperties = new Properties();
        smtpServerProperties.put("mail.smtp.host", emailSenderProperties.getProperty("host"));
        smtpServerProperties.put("mail.smtp.port", emailSenderProperties.getProperty("port"));
        smtpServerProperties.put("mail.smtp.auth", "true");
        smtpServerProperties.put("mail.smtp.starttls.enable", "true");
        return smtpServerProperties;
    }

    private Authenticator createNewSessionAuthenticator() {
        Authenticator auth = new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(emailSenderProperties.getProperty("user_email"), emailSenderProperties.getProperty("user_password"));
            }
        };
        return auth;
    }
}


