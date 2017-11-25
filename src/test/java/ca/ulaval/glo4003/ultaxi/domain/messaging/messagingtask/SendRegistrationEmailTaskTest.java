package ca.ulaval.glo4003.ultaxi.domain.messaging.messagingtask;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

import ca.ulaval.glo4003.ultaxi.domain.messaging.email.Email;
import ca.ulaval.glo4003.ultaxi.domain.messaging.email.EmailSender;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SendRegistrationEmailTaskTest {

    private static final String SEND_TO = "Send.to.user@email.com";
    private static final String RECIPIENT_USERNAME = "username";
    private SendRegistrationEmailTask sendRegistrationEmailTask;

    @Mock
    private EmailSender emailSender;

    @Before
    public void setUp() throws Exception {
        sendRegistrationEmailTask = new SendRegistrationEmailTask(SEND_TO, RECIPIENT_USERNAME, emailSender);
    }

    @Test
    public void givenASendRegistrationEmailTask_whenExecute_thenSendEmailIsCalled() {
        sendRegistrationEmailTask.execute();

        verify(emailSender).sendEmail(any(Email.class));
    }

}
