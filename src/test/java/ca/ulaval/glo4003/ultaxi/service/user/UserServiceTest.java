package ca.ulaval.glo4003.ultaxi.service.user;

import ca.ulaval.glo4003.ultaxi.domain.messaging.MessagingTaskProducer;
import ca.ulaval.glo4003.ultaxi.domain.messaging.email.EmailSender;
import ca.ulaval.glo4003.ultaxi.domain.messaging.messagingtask.MessagingTask;
import ca.ulaval.glo4003.ultaxi.domain.user.TokenManager;
import ca.ulaval.glo4003.ultaxi.domain.user.User;
import ca.ulaval.glo4003.ultaxi.domain.user.UserRepository;
import ca.ulaval.glo4003.ultaxi.transfer.user.UserAssembler;
import ca.ulaval.glo4003.ultaxi.transfer.user.UserDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.BDDMockito.verify;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    private final static String A_VALID_USERNAME = "Valid username";
    private static final String A_VALID_TOKEN = "Valid token";
    private final static String A_VALID_EMAIL = "Valid email";

    @Mock
    private User user;
    @Mock
    private UserDto userDto;
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserAssembler userAssembler;
    @Mock
    private MessagingTaskProducer messagingTaskProducer;
    @Mock
    private EmailSender emailSender;
    @Mock
    private TokenManager tokenManager;

    private UserService userService;

    @Before
    public void setUp() throws Exception {
        willReturn(user).given(userAssembler).create(userDto);
        willReturn(A_VALID_USERNAME).given(user).getUsername();
        willReturn(A_VALID_EMAIL).given(user).getEmailAddress();
        userService = new UserService(userRepository,
                                      userAssembler,
                                      messagingTaskProducer,
                                      emailSender,
                                      tokenManager);
    }

    @Test
    public void givenUserWithValidName_whenAddUser_thenUserIsAdded() {
        userService.addUser(userDto);

        verify(userRepository).save(user);
    }

    @Test
    public void givenANewAddedUser_whenAddUser_thenMessagingTaskProducerIsCalled() {
        userService.addUser(userDto);

        verify(messagingTaskProducer).send(any(MessagingTask.class));
    }

    @Test
    public void givenAClientUpdate_whenUpdateClient_thenClientIsUpdated() {
        when(userService.getUserFromToken(A_VALID_TOKEN)).thenReturn(user);
        willReturn(user).given(userAssembler).create(userDto);

        userService.updateClient(userDto, A_VALID_TOKEN);

        verify(userRepository).update(user);
    }

    @Test
    public void givenAUserToken_whenGetUserFromToken_thenDelegateToTokenManager(){
        userService.getUserFromToken(A_VALID_TOKEN);
        verify(tokenManager).getUsername(A_VALID_TOKEN);
    }

    @Test
    public void givenAUserToken_whenGetUserFromToken_thenDelegateToUserRepository(){
        willReturn(A_VALID_USERNAME).given(tokenManager).getUsername(A_VALID_TOKEN);
        userService.getUserFromToken(A_VALID_TOKEN);
        verify(userRepository).findByUsername(A_VALID_USERNAME);
    }

}