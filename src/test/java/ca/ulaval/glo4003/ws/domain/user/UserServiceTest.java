package ca.ulaval.glo4003.ws.domain.user;

import ca.ulaval.glo4003.ws.api.user.dto.UserDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.BDDMockito.verify;
import static org.mockito.BDDMockito.willReturn;


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

    private UserService userService;

    @Before
    public void setUp() throws Exception {
        userService = new UserService(userRepository, userAssembler);
        willReturn(user).given(userAssembler).create(userDto);
    }

    @Test
    public void givenUserWithValidName_whenAddUser_thenUserIsAdded() {
        userService.addUser(userDto);

        verify(userRepository).save(user);
    }
}