package ca.ulaval.glo4003.ws.infrastructure.messaging;

import ca.ulaval.glo4003.ws.domain.messaging.Email;
import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.GreenMailUtil;
import com.icegreen.greenmail.util.ServerSetup;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import javax.mail.Message;
import javax.mail.MessagingException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class EmailSenderTest {

    private static final int SERVER_PORT = 3025;
    private static final String BIND_ADDRESS = null;
    private static final String PROTOCOL = "smtp";
    private static final String TO_ADDRESS = "to@localhost.com";
    private static final String EMAIL_SUBJECT = "Subject";
    private static final String EMAIL_CONTENT = "Content";
    private static final String EMAIL_SIGNATURE = "-Signature";
    private static String EMAIL_SENDER_CONFIGURATION_FILENAME = "emailSenderConfigurationTest.properties";

    private EmailSender emailSender;
    private GreenMail greenMail;

    @Before
    public void setUp() throws Exception {
        greenMail = new GreenMail(new ServerSetup(SERVER_PORT, BIND_ADDRESS, PROTOCOL));
        greenMail.start();
        emailSender = new EmailSender(EMAIL_SENDER_CONFIGURATION_FILENAME);
    }

    @After
    public void tearDown() throws Exception {
        greenMail.stop();
    }

    @Test
    public void givenAnEmail_whenIsSend_thenReceivedEmailShouldBeTheSame() throws MessagingException {
        Email email = new Email(TO_ADDRESS, EMAIL_SUBJECT, EMAIL_CONTENT, EMAIL_SIGNATURE);

        emailSender.sendEmail(email);

        assertTrue(greenMail.waitForIncomingEmail(5000, 1));
        Message[] messages = greenMail.getReceivedMessages();
        String body = GreenMailUtil.getBody(messages[0]).replaceAll("=\r?\n", "");
        assertEquals(1, messages.length);
        assertEquals("Subject", messages[0].getSubject());
        assertEquals("Content-Signature", body);
    }
}
