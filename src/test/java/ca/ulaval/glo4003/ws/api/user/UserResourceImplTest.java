package ca.ulaval.glo4003.ws.api.user;

import ca.ulaval.glo4003.ws.api.user.dto.UserDto;
import ca.ulaval.glo4003.ws.domain.user.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.BDDMockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class UserResourceImplTest {

    @Mock
    private UserService userService;
    @Mock
    private UserDto userDto;

    private UserResource userResource;

    @Before
    public void setUp() throws Exception {
        userResource = new UserResourceImpl(userService);
    }

    @Test
    public void givenUserWithValidName_whenCreateUser_thenUserIsCreated() {
        userResource.createUser(userDto);

        verify(userService).addUser(userDto);
    }
}