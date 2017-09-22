package ca.ulaval.glo4003.ws.infrastructure.user;

import ca.ulaval.glo4003.ws.domain.user.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class UserRepositoryInMemoryTest {

    private static final String CONTACT_ID = "id";

    @Mock
    private User user;

    private UserRepositoryInMemory contactRepositoryInMemory;

    @Before
    public void setUp() {
        contactRepositoryInMemory = new UserRepositoryInMemory();
        BDDMockito.given(user.getId()).willReturn(CONTACT_ID);
    }

    @Test
    public void givenContact_whenFindAll_ThenReturnContactInMemory() {
        //given
        contactRepositoryInMemory.save(user);

        // when
        List<User> users = contactRepositoryInMemory.findAll();

        // then
        assertThat(users, org.hamcrest.Matchers.hasItem(user));
    }

}