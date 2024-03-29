package ca.ulaval.glo4003.ultaxi.infrastructure.messaging.email;

import ca.ulaval.glo4003.ultaxi.domain.messaging.email.Email;
import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.GreenMailUtil;
import com.icegreen.greenmail.util.ServerSetup;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.mail.Message;
import javax.mail.MessagingException;
import java.util.Properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.willReturn;

@RunWith(MockitoJUnitRunner.class)
public class JavaMailEmailSenderTest {

    private static final int SERVER_PORT = 3025;
    private static final String SERVER_HOST = "localhost";
    private static final String BIND_ADDRESS = null;
    private static final String PROTOCOL = "smtp";
    private static final String TO_ADDRESS = "to@localhost.com";
    private static final String TO_ANOTHER_ADDRESS = "to.another@localhost.com";
    private static final String EMAIL_SUBJECT = "Subject";
    private static final String EMAIL_CONTENT = "Content";
    private static final String EMAIL_SIGNATURE = "-Signature";

    @Mock
    private EmailSenderConfigurationReader emailSenderConfigurationReader;
    private JavaMailEmailSender javaMailEmailSender;
    private GreenMail greenMail;

    @Before
    public void setUp() throws Exception {
        greenMail = new GreenMail(new ServerSetup(SERVER_PORT, BIND_ADDRESS, PROTOCOL));
        greenMail.start();
        willReturn(someProperties()).given(emailSenderConfigurationReader).read();
        javaMailEmailSender = new JavaMailEmailSender(emailSenderConfigurationReader);
    }

    @After
    public void tearDown() throws Exception {
        greenMail.stop();
    }

    @Test
    public void givenAnEmail_whenIsSend_thenEmailSenderSendSuccessfully() throws
        MessagingException {
        Email email = new Email(TO_ADDRESS, EMAIL_SUBJECT, EMAIL_CONTENT, EMAIL_SIGNATURE);

        javaMailEmailSender.sendEmail(email);

        assertTrue(greenMail.waitForIncomingEmail(5000, 1));
    }

    @Test
    public void givenMultipleEmailsToBeSend_whenIsSend_thenEmailSenderSendSuccessfully() throws
        MessagingException {
        Email firstEmail = new Email(TO_ADDRESS, EMAIL_SUBJECT, EMAIL_CONTENT, EMAIL_SIGNATURE);
        Email secondEmail = new Email(TO_ANOTHER_ADDRESS, EMAIL_SUBJECT, EMAIL_CONTENT, EMAIL_SIGNATURE);

        javaMailEmailSender.sendEmail(firstEmail);
        javaMailEmailSender.sendEmail(secondEmail);

        assertTrue(greenMail.waitForIncomingEmail(5000, 2));
    }

    @Test
    public void givenAnEmail_whenIsSend_thenReceivedEmailShouldBeTheSame() throws
        MessagingException {
        Email email = new Email(TO_ADDRESS, EMAIL_SUBJECT, EMAIL_CONTENT, EMAIL_SIGNATURE);

        javaMailEmailSender.sendEmail(email);

        Message[] messages = greenMail.getReceivedMessages();
        String body = GreenMailUtil.getBody(messages[0]).replaceAll("=\r?\n", "");
        assertEquals(1, messages.length);
        assertEquals("Subject", messages[0].getSubject());
        assertEquals("Content-Signature", body);
    }

    private Properties someProperties() {
        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.port", String.valueOf(SERVER_PORT));
        properties.setProperty("mail.smtp.host", SERVER_HOST);
        return properties;
    }
}
