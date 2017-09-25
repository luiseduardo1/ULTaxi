package ca.ulaval.glo4003.ws.infrastructure.user;

import ca.ulaval.glo4003.ws.domain.user.User;
import ca.ulaval.glo4003.ws.domain.user.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class UserRepositoryInMemoryTest {

    private User user;
    private UserRepository userRepository;

    @Before
    public void setUp() throws Exception {
        userRepository = new UserRepositoryInMemory();
        user = new User();
        user.setName("Ronald");
        user.setPassword("Beaubrun");
    }

    @Test
    public void givenUser_whenSave_thenUserHasSameParameters() {
        userRepository.save(user);
        User anotherUser = userRepository.findByName(user.getName());

        assertEquals(user, anotherUser);
    }
}