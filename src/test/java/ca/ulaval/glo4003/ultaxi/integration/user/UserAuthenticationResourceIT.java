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
        String serializedUser = createSerializedValidUser();
        unauthenticatedPost(USERS_ROUTE, serializedUser);

        Response response = authenticateAs(serializedUser);

        assertStatusCode(response, Status.OK);
    }

    @Test
    public void givenNonexistentUser_whenAuthenticate_thenReturnsForbidden() {
        String serializedUser = createSerializedValidUser();

        Response response = authenticateAs(serializedUser);

        assertStatusCode(response, Status.FORBIDDEN);
    }

    @Test
    public void givenCredentialsWithWrongPassword_whenAuthenticate_thenReturnsForbidden() {
        String serializedUser = createSerializedValidUser();
        unauthenticatedPost(USERS_ROUTE, serializedUser);

        String serializedUserWithWrongPassword = createSerializedUserWithWrongPassword();
        Response response = authenticateAs(serializedUserWithWrongPassword);

        assertStatusCode(response, Status.FORBIDDEN);
    }

    @Test
    public void givenAuthenticatedUser_whenSignOut_thenUserIsSignedOut() {
        String serializedUser = createSerializedValidUser();
        unauthenticatedPost(USERS_ROUTE, serializedUser);
        authenticateAs(serializedUser);

        Response response = signout();

        assertStatusCode(response, Status.RESET_CONTENT);
    }

    @Test
    public void givenUnauthenticatedUser_whenSignOut_thenReturnsBadRequest() {
        Response response = signout();

        assertStatusCode(response, Status.BAD_REQUEST);
    }

    private String createSerializedValidUser() {
        return createSerializedUser(
            generateRandomWord(),
            A_VALID_PASSWORD,
            A_VALID_EMAIL
        );
    }

    private String createSerializedUserWithWrongPassword() {
        return createSerializedUser(
            generateRandomWord(),
            A_DIFFERENT_PASSWORD,
            A_VALID_EMAIL
        );
    }
}
