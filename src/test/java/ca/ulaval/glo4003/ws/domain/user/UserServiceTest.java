package ca.ulaval.glo4003.ws.domain.user;

import static org.mockito.BDDMockito.verify;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Matchers.any;

import ca.ulaval.glo4003.ws.api.user.dto.UserDto;
import ca.ulaval.glo4003.ws.domain.messaging.Message;
import ca.ulaval.glo4003.ws.domain.messaging.MessageQueueProducer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    private User user;
    @Mock
    private UserDto userDto;
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserAssembler userAssembler;
    @Mock
    private UserAuthenticationService userAuthenticationService;
    @Mock
    private MessageQueueProducer messageQueueProducer;
    @Mock
    private Message message;

    private UserService userService;

    @Before
    public void setUp() throws Exception {
        userService = new UserService(userRepository,
                                      userAssembler,
                                      userAuthenticationService,
                                      messageQueueProducer);
    }

    @Test
    public void givenUserWithValidName_whenAddUser_thenUserIsAdded() {
        willReturn(user).given(userAssembler).create(userDto);

        userService.addUser(userDto);

        verify(userRepository).save(user);
    }

    @Test
    public void givenUserWithValidCredentials_whenAuthenticating_thenUserIsAuthenticated() {
        willReturn(user).given(userAssembler).create(userDto);

        userService.authenticate(userDto);

        verify(userAuthenticationService).authenticate(user);
    }

    @Test
    public void givenANewAddedUser_whenAddUser_thenMessagingServiceIsCalled() {
        willReturn(user).given(userAssembler).create(userDto);

        userService.addUser(userDto);

        verify(messageQueueProducer).send(any(Message.class));
    }
}