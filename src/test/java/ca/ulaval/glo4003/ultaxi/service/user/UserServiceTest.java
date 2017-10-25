package ca.ulaval.glo4003.ultaxi.service.user;

import static org.mockito.BDDMockito.verify;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Matchers.any;

import ca.ulaval.glo4003.ultaxi.domain.messaging.MessagingTaskProducer;
import ca.ulaval.glo4003.ultaxi.domain.messaging.email.EmailSender;
import ca.ulaval.glo4003.ultaxi.domain.messaging.messagingtask.MessagingTask;
import ca.ulaval.glo4003.ultaxi.domain.user.User;
import ca.ulaval.glo4003.ultaxi.domain.user.UserRepository;
import ca.ulaval.glo4003.ultaxi.transfer.user.UserAssembler;
import ca.ulaval.glo4003.ultaxi.transfer.user.UserDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    private final static String A_VALID_USERNAME = "Valid username";
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

    private UserService userService;

    @Before
    public void setUp() throws Exception {
        willReturn(user).given(userAssembler).create(userDto);
        willReturn(A_VALID_USERNAME).given(user).getUsername();
        willReturn(A_VALID_EMAIL).given(user).getEmailAddress();
        userService = new UserService(userRepository,
                                      userAssembler,
                                      messagingTaskProducer,
                                      emailSender);
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
}