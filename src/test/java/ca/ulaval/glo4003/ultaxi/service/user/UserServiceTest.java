package ca.ulaval.glo4003.ultaxi.service.user;

import static org.mockito.BDDMockito.verify;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Matchers.any;

import ca.ulaval.glo4003.ultaxi.domain.messaging.TaskQueueProducer;
import ca.ulaval.glo4003.ultaxi.domain.user.User;
import ca.ulaval.glo4003.ultaxi.domain.user.UserRepository;
import ca.ulaval.glo4003.ultaxi.infrastructure.messaging.EmailSender;
import ca.ulaval.glo4003.ultaxi.transfer.user.UserAssembler;
import ca.ulaval.glo4003.ultaxi.transfer.user.UserDto;
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
    private TaskQueueProducer taskQueueProducer;
    @Mock
    private EmailSender emailSender;

    private UserService userService;

    @Before
    public void setUp() throws Exception {
        userService = new UserService(userRepository,
                userAssembler,
                taskQueueProducer,
                emailSender);
    }

    @Test
    public void givenUserWithValidName_whenAddUser_thenUserIsAdded() {
        willReturn(user).given(userAssembler).create(userDto);

        userService.addUser(userDto);

        verify(userRepository).save(user);
    }

    @Test
    public void givenANewAddedUser_whenAddUser_thenMessagingServiceIsCalled() {
        willReturn(user).given(userAssembler).create(userDto);

        userService.addUser(userDto);

        verify(taskQueueProducer).send(any(Runnable.class));
    }
}