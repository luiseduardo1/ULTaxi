package ca.ulaval.glo4003.ultaxi.domain.messaging.messagingtask;

import ca.ulaval.glo4003.ultaxi.domain.messaging.sms.Sms;
import ca.ulaval.glo4003.ultaxi.domain.messaging.sms.SmsSender;
import ca.ulaval.glo4003.ultaxi.domain.messaging.sms.exception.UnrecoverableSmsSendingFailureException;
import ca.ulaval.glo4003.ultaxi.domain.transportrequest.TransportRequest;
import ca.ulaval.glo4003.ultaxi.domain.transportrequest.TransportRequestRepository;
import ca.ulaval.glo4003.ultaxi.domain.user.PhoneNumber;
import ca.ulaval.glo4003.ultaxi.domain.user.User;
import ca.ulaval.glo4003.ultaxi.domain.user.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class SendDriverHasArrivedSmsTaskTest {

    private static final String A_TRANSPORT_REQUEST_ID = "Some id";
    private static final String VALID_PHONE_NUMBER = "5005550006";
    private static final String VALID_DESTINATION_PHONE_NUMBER = "2342355678";
    private static final String A_USERNAME = "john_smith_1337";

    @Mock
    private UserRepository userRepository;
    @Mock
    private TransportRequestRepository transportRequestRepository;
    @Mock
    private TransportRequest transportRequest;
    @Mock
    private SmsSender smsSender;
    @Mock
    private User user;
    @Mock
    private PhoneNumber phoneNumber;

    private SendDriverHasArrivedSmsTask sendDriverHasArrivedSmsTask;

    @Before
    public void setUp() {
        sendDriverHasArrivedSmsTask = new SendDriverHasArrivedSmsTask(VALID_PHONE_NUMBER, smsSender,
                                                                      A_TRANSPORT_REQUEST_ID,
                                                                      transportRequestRepository, userRepository);
        willReturn(transportRequest).given(transportRequestRepository).findById(A_TRANSPORT_REQUEST_ID);
        willReturn(A_USERNAME).given(transportRequest).getClientUsername();
        willReturn(user).given(userRepository).findByUsername(anyString());
        willReturn(phoneNumber).given(user).getPhoneNumber();
        willReturn(VALID_DESTINATION_PHONE_NUMBER).given(phoneNumber).getNumber();
    }

    @Test
    public void givenASendDriverHasArrivedSmsTask_whenExecuting_thenSendSmsIsCalled() {
        sendDriverHasArrivedSmsTask.execute();

        verify(smsSender).sendSms(any(Sms.class));
    }

    @Test(expected = UnrecoverableSmsSendingFailureException.class)
    public void givenInexistingClient_whenExecuting_thenThrowsUnrecoverableSmsSendingFailureException() {
        willReturn(null).given(userRepository).findByUsername(anyString());
        sendDriverHasArrivedSmsTask.execute();
    }

    @Test(expected = UnrecoverableSmsSendingFailureException.class)
    public void givenInexistingTransportRequest_whenExecuting_thenThrowsUnrecoverableSmsSendingFailureException() {
        willReturn(null).given(transportRequestRepository).findById(anyString());
        sendDriverHasArrivedSmsTask.execute();
    }
}