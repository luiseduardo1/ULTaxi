package ca.ulaval.glo4003.ws.api.user;

import ca.ulaval.glo4003.ws.api.user.dto.UserDto;
import ca.ulaval.glo4003.ws.domain.user.UserService;
import jersey.repackaged.com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.junit.Assert.assertThat;

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
    public void whenFindAllContacts_thenDelegateToService() {
        // given
        BDDMockito.given(userService.findAllUsers())
            .willReturn(Lists.newArrayList(userDto));

        // when
        List<UserDto> userDtos = userResource.getUsers();

        // then
        assertThat(userDtos, org.hamcrest.Matchers.hasItem(userDto));
        Mockito.verify(userService).findAllUsers();
    }

}