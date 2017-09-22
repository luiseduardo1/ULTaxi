package ca.ulaval.glo4003.ws.domain.user;

import ca.ulaval.glo4003.ws.api.user.dto.UserDto;
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
    }

    @Test
    public void givenContactsInRepository_whenFindAllContacts_thenReturnDtos()
        throws Exception {
        // given
        BDDMockito.given(userRepository.findAll()).willReturn(Lists.newArrayList(user));
        BDDMockito.given(userAssembler.create(user)).willReturn(userDto);

        // when
        List<UserDto> userDtos = userService.findAllUsers();

        // then
        assertThat(userDtos, org.hamcrest.Matchers.hasItem(userDto));
        Mockito.verify(userRepository).findAll();
        Mockito.verify(userAssembler).create(org.mockito.Matchers.eq(user));
    }

}