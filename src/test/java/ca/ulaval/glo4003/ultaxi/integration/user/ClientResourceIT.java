package ca.ulaval.glo4003.ultaxi.integration.user;

import ca.ulaval.glo4003.ultaxi.integration.IntegrationTest;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ws.rs.core.Response.Status;

@RunWith(MockitoJUnitRunner.class)
public class ClientResourceIT extends IntegrationTest {

    private static final String A_VALID_PASSWORD = "userPassword";
    private static final String A_VALID_EMAIL = "valid.email.test@gmail.com";
    private static final String A_VALID_PHONE_NUMBER = "581-319-0987";
    private static final String AN_INVALID_NAME = "ronald.macdonald@ulaval.ca";
    private static final String AN_INVALID_EMAIL = "invalid.email.gmail.com";
    private static final String AN_EMPTY_PASSWORD = "";
    private final String aValidClient = createSerializedValidClient();
    private boolean isUserCreated = false;

    @Before
    public void setUp() {
        if (!isUserCreated) {
            unauthenticatedPost(CLIENTS_ROUTE, aValidClient);
            isUserCreated = true;
        }
        authenticateAs(aValidClient);
    }

    @Test
    public void givenClientWithValidName_whenCreateClient_thenClientIsCreated() {
        String serializedUser = createSerializedValidClient();

        Response response = unauthenticatedPost(CLIENTS_ROUTE, serializedUser);

        assertStatusCode(response, Status.OK);
    }

    @Test
    public void givenAlreadyExistingClient_whenCreateClient_thenReturnsBadRequest() {
        String serializedUser = createSerializedValidClient();
        unauthenticatedPost(CLIENTS_ROUTE, serializedUser);

        Response response = unauthenticatedPost(CLIENTS_ROUTE, serializedUser);

        assertStatusCode(response, Status.BAD_REQUEST);
    }

    @Test
    public void givenClientWithInvalidName_whenCreateClient_thenReturnsBadRequest() {
        String serializedUser = createSerializedClientWithInvalidName();

        Response response = unauthenticatedPost(CLIENTS_ROUTE, serializedUser);

        assertStatusCode(response, Status.BAD_REQUEST);
    }

    @Test
    public void givenAlreadyExistingClient_whenUpdateClient_thenClientIsUpdated() {
        String serializedUser = createSerializedValidClient();

        Response response = authenticatedPut(CLIENTS_ROUTE, serializedUser);

        assertStatusCode(response, Status.OK);
    }

    @Test
    public void givenAnUnauthenticatedClient_whenUpdateClient_thenReturnsUnauthorized() {
        String serializedUser = createSerializedValidClient();

        Response response = unauthenticatedPut(CLIENTS_ROUTE, serializedUser);

        assertStatusCode(response, Status.UNAUTHORIZED);
    }

    @Test
    public void givenAClientWithAnEmptyPassword_whenCreatingClient_thenReturnsBadRequest() {
        String serializedUser = createSerializedClientWithEmptyPassword();

        Response response = unauthenticatedPost(CLIENTS_ROUTE, serializedUser);

        assertStatusCode(response, Status.BAD_REQUEST);
    }

    @Test
    public void givenAClientWithAnEmptyPassword_whenUpdatingClient_thenReturnsUnauthorized() {
        String serializedUser = createSerializedClientWithEmptyPassword();

        Response response = authenticatedPut(CLIENTS_ROUTE, serializedUser);

        assertStatusCode(response, Status.BAD_REQUEST);
    }

    @Test
    public void givenAClientWithAnInvalidEmail_whenUpdatingClient_thenReturnsBadRequest() {
        String serializedUser = createSerializedClientWithInvalidEmail();

        Response response = authenticatedPut(CLIENTS_ROUTE, serializedUser);

        assertStatusCode(response, Status.BAD_REQUEST);
    }

    private String createSerializedValidClient() {
        return createSerializedClient(
            generateRandomWord(),
            A_VALID_PASSWORD,
            A_VALID_PHONE_NUMBER,
            A_VALID_EMAIL
        );
    }

    private String createSerializedClientWithInvalidName() {
        return createSerializedClient(
            AN_INVALID_NAME,
            A_VALID_PASSWORD,
            A_VALID_PHONE_NUMBER,
            A_VALID_EMAIL
        );
    }

    private String createSerializedClientWithInvalidEmail() {
        return createSerializedClient(
            generateRandomWord(),
            A_VALID_PASSWORD,
            A_VALID_PHONE_NUMBER,
            AN_INVALID_EMAIL
        );
    }

    private String createSerializedClientWithEmptyPassword() {
        return createSerializedClient(
            generateRandomWord(),
            AN_EMPTY_PASSWORD,
            A_VALID_PHONE_NUMBER,
            A_VALID_EMAIL
        );
    }
}
