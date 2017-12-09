package ca.ulaval.glo4003.ultaxi.infrastructure.messaging.sms;

import ca.ulaval.glo4003.ultaxi.domain.messaging.sms.Sms;
import ca.ulaval.glo4003.ultaxi.domain.messaging.sms.SmsSender;
import ca.ulaval.glo4003.ultaxi.domain.messaging.sms.exception.SmsSendingFailureException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Random;

import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Matchers.anyInt;

@RunWith(MockitoJUnitRunner.class)
public class SmsSenderStubTest {

    private static final int A_FAILING_NUMBER = 1;

    @Mock
    private Random random;
    @Mock
    private Sms sms;
    private SmsSender smsSender;

    @Before
    public void setUp() {
        this.smsSender = new SmsSenderStub(random);
    }

    @Test(expected = SmsSendingFailureException.class)
    public void givenASendingFailure_whenSendingSms_thenThrowsSmsSendingFailureException() {
        willReturn(A_FAILING_NUMBER).given(random).nextInt(anyInt());

        smsSender.sendSms(sms);
    }
}