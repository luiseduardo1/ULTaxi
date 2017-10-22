package ca.ulaval.glo4003.ultaxi.integration.user;

import ca.ulaval.glo4003.ultaxi.integration.IntegrationTest;
import io.restassured.response.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ws.rs.core.Response.Status;

@RunWith(MockitoJUnitRunner.class)
public class UserAuthenticationResourceIT extends IntegrationTest {

    private static final String A_VALID_PASSWORD = "Macdonald";
    private static final String A_VALID_EMAIL = "valid.email.test@gmail.com";
    private static final String A_DIFFERENT_PASSWORD = "Nadir";

    @Test
    public void givenUser_whenAuthenticate_thenUserIsAuthenticated() {
        String userData = givenUser();
        unauthenticatedPost(USERS_ROUTE, userData);

        Response response = authenticateAs(userData);

        assertStatusCode(response, Status.OK);
    }

    @Test
    public void givenInexistingUser_whenAuthenticate_thenReturnsForbidden() {
        String userData = givenUser();

        Response response = authenticateAs(userData);

        assertStatusCode(response, Status.FORBIDDEN);
    }

    @Test
    public void givenInvalidCredentials_whenAuthenticate_thenReturnsForbidden() {
        String userWithInvalidPasswordData = givenUserWithInvalidPassword();
        String userData = givenUser();
        unauthenticatedPost(USERS_ROUTE, userData);

        Response response = authenticateAs(userWithInvalidPasswordData);

        assertStatusCode(response, Status.FORBIDDEN);
    }

    @Test
    public void givenAuthenticatedUser_whenSignOut_thenUserIsSignedOut() {
        String userData = givenUser();
        unauthenticatedPost(USERS_ROUTE, userData);
        authenticateAs(userData);

        Response response = signout();

        assertStatusCode(response, Status.RESET_CONTENT);
    }

    private String givenUser() {
        return createUserData(generateRandomWord(), A_VALID_PASSWORD, A_VALID_EMAIL);
    }

    private String givenUserWithInvalidPassword() {
        return createUserData(generateRandomWord(), A_DIFFERENT_PASSWORD, A_VALID_EMAIL);
    }
}
