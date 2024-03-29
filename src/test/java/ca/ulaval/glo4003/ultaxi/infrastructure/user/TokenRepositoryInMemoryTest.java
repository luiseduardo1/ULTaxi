package ca.ulaval.glo4003.ultaxi.infrastructure.user;

import ca.ulaval.glo4003.ultaxi.domain.user.TokenRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TokenRepositoryInMemoryTest {

    private static final String TOKEN = "token";
    private static final String ID = "id";
    private TokenRepository tokenRepository;

    @Before
    public void setUp() {
        tokenRepository = new TokenRepositoryInMemory();
        tokenRepository.save(ID, TOKEN);
    }

    @Test
    public void givenATokenToAdd_whenAddingAToken_ThenTokenIsAdded() {
        Assert.assertEquals(tokenRepository.getToken(ID), TOKEN);
    }

    @Test
    public void givenATokenToDelete_whenDeleting_ThenTokenIsDeleted() {
        tokenRepository.delete(ID);
        Assert.assertNull(tokenRepository.getToken(ID));
    }
}
