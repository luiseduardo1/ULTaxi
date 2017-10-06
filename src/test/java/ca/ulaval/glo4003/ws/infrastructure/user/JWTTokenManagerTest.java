package ca.ulaval.glo4003.ws.infrastructure.user;

import ca.ulaval.glo4003.ws.infrastructure.user.JWT.InvalidTokenException;
import ca.ulaval.glo4003.ws.infrastructure.user.JWT.JWTTokenManager;
import io.jsonwebtoken.impl.DefaultClaims;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.concurrent.TimeUnit;


@RunWith(MockitoJUnitRunner.class)
public class JWTTokenManagerTest {

    private JWTTokenManager jwtTokenManager;

    private String A_VALID_SUBJECT = "Bob";

    private long A_VALID_TTLINMILLIS = 56000000;

    private String A_VALID_TOKEN;

    @Before
    public void setUp() {
        jwtTokenManager = new JWTTokenManager();
        this.A_VALID_TOKEN = jwtTokenManager.createToken(A_VALID_SUBJECT, A_VALID_TTLINMILLIS);
    }

    @Test
    public void givenValidParameters_whenCreatingAToken_thenATokenIsCreated() {
        String token = jwtTokenManager.createToken(A_VALID_SUBJECT, A_VALID_TTLINMILLIS);
        Assert.assertNotNull(token);
    }

    @Test
    public void givenNoExpiration_whenCreatingAToken_ThenATokenIsCreatedWithoutExpiration() {
        long NULL_TTL = 0;
        String token = jwtTokenManager.createToken(A_VALID_SUBJECT, NULL_TTL);
        Assert.assertNull(jwtTokenManager.parseToken(token).getExpiration());
    }

    @Test
    public void givenAParsableToken_whenParsingAToken_thenTokenIsParsed() {
        Assert.assertEquals(jwtTokenManager.parseToken(this.A_VALID_TOKEN).getClass(),
                            DefaultClaims.class);
    }

    @Test(expected = InvalidTokenException.class)
    public void givenAnNonParsableToken_whenParsingAToken_thenAnExceptionIsThrown() {
        jwtTokenManager.parseToken(null);
    }

    @Test
    public void givenANonExpiredToken_whenValidatingAToken_thenNoExceptionIsThrown() {
        jwtTokenManager.validateTokenExpirationDate(this.A_VALID_TOKEN);
    }

    @Test(expected = InvalidTokenException.class)
    public void givenAnExpiredToken_whenValidatingAToken_thenInvalidTokenExceptionIsThrown()
        throws InterruptedException {
        long shortTTL = 1;
        String expiredToken = jwtTokenManager.createToken(A_VALID_SUBJECT, shortTTL);
        TimeUnit.MILLISECONDS.sleep(2);
        jwtTokenManager.validateTokenExpirationDate(expiredToken);
    }

    @Test
    public void givenATokenWithASubject_whenGettingTheSubject_ThenSubjectIsTheSame() {
        Assert.assertEquals(jwtTokenManager.getUsername(this.A_VALID_TOKEN), A_VALID_SUBJECT);
    }
}
