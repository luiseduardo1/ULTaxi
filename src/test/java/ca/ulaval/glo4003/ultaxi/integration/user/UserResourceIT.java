package ca.ulaval.glo4003.ultaxi.integration.user;

import ca.ulaval.glo4003.ultaxi.domain.user.Role;
import ca.ulaval.glo4003.ultaxi.integration.IntegrationTest;
import io.restassured.response.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ws.rs.core.Response.Status;

@RunWith(MockitoJUnitRunner.class)
public class UserResourceIT extends IntegrationTest {

    private static final String A_VALID_PASSWORD = "Macdonald";
    private static final String A_VALID_EMAIL = "valid.email.test@gmail.com";
    private static final String AN_INVALID_NAME = "ronald.macdonald@ulaval.ca";

    @Test
    public void givenUserWithValidName_whenCreateUser_thenUserIsCreated() {
        String serializedUser = createSerializedValidUser();

        Response response = unauthenticatedPost(USERS_ROUTE, serializedUser);

        assertStatusCode(response, Status.OK);
    }

    @Test
    public void givenAlreadyExistingUser_whenCreateUser_thenReturnsBadRequest() {
        String serializedUser = createSerializedValidUser();
        unauthenticatedPost(USERS_ROUTE, serializedUser);

        Response response = unauthenticatedPost(USERS_ROUTE, serializedUser);

        assertStatusCode(response, Status.BAD_REQUEST);
    }

    @Test
    public void givenUserWithInvalidName_whenCreateUser_thenReturnsBadRequest() {
        String serializedUser = createSerializedUserWithInvalidName();

        Response response = unauthenticatedPost(USERS_ROUTE, serializedUser);

        assertStatusCode(response, Status.BAD_REQUEST);
    }

    @Test
    public void givenAlreadyExistingUser_whenUpdateUser_thenUserIsUpdated() {
        authenticateAs(Role.DRIVER);
        String serializedUser = createSerializedValidUser();

        Response response = authenticatedPut(USERS_ROUTE + "/update", serializedUser);

        assertStatusCode(response, Status.OK);
    }

    @Test
    public void givenAnUnauthenticatedUser_whenUpdateUser_thenReturnsUnauthorized() {
        String serializedUser = createSerializedValidUser();

        Response response = unauthenticatedPut(USERS_ROUTE + "/update", serializedUser);

        assertStatusCode(response, Status.UNAUTHORIZED);
    }

    private String createSerializedValidUser() {
        return createSerializedUser(
            generateRandomWord(),
            A_VALID_PASSWORD,
            A_VALID_EMAIL
        );
    }

    private String createSerializedUserWithInvalidName() {
        return createSerializedUser(
            AN_INVALID_NAME,
            A_VALID_PASSWORD,
            A_VALID_EMAIL
        );
    }
}
