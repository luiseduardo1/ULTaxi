package ca.ulaval.glo4003.ultaxi.infrastructure.messaging.sms;

import static org.mockito.BDDMockito.willReturn;

import ca.ulaval.glo4003.ultaxi.domain.messaging.sms.Sms;
import ca.ulaval.glo4003.ultaxi.domain.messaging.sms.SmsSender;
import ca.ulaval.glo4003.ultaxi.domain.messaging.sms.exception.UnrecoverableSmsSendingFailureException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Properties;

@RunWith(MockitoJUnitRunner.class)
public class TwilioSmsSenderTest {

    private static final String securityIdentifier = "ACf811e58eb6cde85b3a778544bc11b1c6";
    private static final String authenticationToken = "6417135537deb8125c6d6a245e4bc57f";
    private static final String UNAVAILABLE_PHONE_NUMBER = "5005550000";
    private static final String INVALID_PHONE_NUMBER = "5005550001";
    private static final String UNROUTABLE_PHONE_NUMBER = "5005550002";
    private static final String PHONE_NUMBER_THAT_REQUIRES_MISSING_PERMISSIONS = "5005550003";
    private static final String BLACKLISTED_PHONE_NUMBER = "5005550004";
    private static final String VALID_PHONE_NUMBER = "5005550006";
    private static final String VALID_DESTINATION_PHONE_NUMBER = "2342355678";
    private static final String A_MESSAGE = "Test";

    @Mock
    private SmsSenderConfigurationReader smsSenderConfigurationReader;
    private SmsSender smsSender;

    @Before
    public void setUp() {
        willReturn(someProperties()).given(smsSenderConfigurationReader).read();
        this.smsSender = new TwilioSmsSender(this.smsSenderConfigurationReader);
    }

    @Test(expected = UnrecoverableSmsSendingFailureException.class)
    public void givenBlacklistedPhoneNumber_whenSendingSms_thenThrowsUnrecoverableSmsSendingFailureException() {
        Sms sms = new Sms(BLACKLISTED_PHONE_NUMBER, INVALID_PHONE_NUMBER, A_MESSAGE);

        smsSender.sendSms(sms);
    }

    @Test(expected = UnrecoverableSmsSendingFailureException.class)
    public void
    givenPhoneNumberThatRequiresMissingPermissions_whenSendingSms_thenThrowsUnrecoverableSmsSendingFailureException() {
        Sms sms = new Sms(PHONE_NUMBER_THAT_REQUIRES_MISSING_PERMISSIONS, INVALID_PHONE_NUMBER, A_MESSAGE);

        smsSender.sendSms(sms);
    }

    @Test(expected = UnrecoverableSmsSendingFailureException.class)
    public void givenUnroutablePhoneNumber_whenSendingSms_thenThrowsUnrecoverableSmsSendingFailureException() {
        Sms sms = new Sms(UNROUTABLE_PHONE_NUMBER, INVALID_PHONE_NUMBER, A_MESSAGE);

        smsSender.sendSms(sms);
    }

    @Test(expected = UnrecoverableSmsSendingFailureException.class)
    public void givenInvalidSourcePhoneNumber_whenSendingSms_thenThrowsUnrecoverableSmsSendingFailureException() {
        Sms sms = new Sms(VALID_PHONE_NUMBER, INVALID_PHONE_NUMBER, A_MESSAGE);

        smsSender.sendSms(sms);
    }

    @Test(expected = UnrecoverableSmsSendingFailureException.class)
    public void givenUnavailableSourcePhoneNumber_whenSendingSms_thenThrowsUnrecoverableSmsSendingFailureException() {
        Sms sms = new Sms(VALID_PHONE_NUMBER, UNAVAILABLE_PHONE_NUMBER, A_MESSAGE);

        smsSender.sendSms(sms);
    }

    @Test
    public void givenValidPhoneNumbers_whenSendingSms_thenSmsIsSent() {
        Sms sms = new Sms(VALID_DESTINATION_PHONE_NUMBER, VALID_PHONE_NUMBER, A_MESSAGE);

        smsSender.sendSms(sms);
    }

    private Properties someProperties() {
        Properties properties = System.getProperties();
        properties.setProperty("sms.twilio.sid", securityIdentifier);
        properties.setProperty("sms.twilio.auth_token", authenticationToken);
        return properties;
    }
}
