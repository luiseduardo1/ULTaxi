package ca.ulaval.glo4003.ultaxi.integration.user;

import ca.ulaval.glo4003.ultaxi.infrastructure.user.CommonUser;
import ca.ulaval.glo4003.ultaxi.integration.IntegrationTest;
import ca.ulaval.glo4003.ultaxi.transfer.user.UserDto;
import com.google.gson.Gson;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ws.rs.core.Response.Status;

@RunWith(MockitoJUnitRunner.class)
public class UserResourceIT extends IntegrationTest {

    private static final String A_VALID_PASSWORD = "userPassword";
    private static final String AN_INVALID_NAME = "ronald.macdonald@ulaval.ca";
    private static final String AN_INVALID_EMAIL = "invalid.email.gmail.com";
    private static final String A_VALID_EMAIL = "valid.email.test@gmail.com";
    private static final String AN_EMPTY_PASSWORD = "";
    private static final String A_VALID_USER = createSerializedValidUser();
    private static boolean isUserCreated = false;

    private CommonUser commonUser;

    @Before
    public void setUp() {
        if (!isUserCreated) {
            unauthenticatedPost(USERS_ROUTE, A_VALID_USER);
            isUserCreated = true;
        }
        authenticateAs(A_VALID_USER);

    }

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
    public void givenAlreadyExistingUser_whenUpdateClient_thenUserIsUpdated() {
        authenticateAs(createSerializedClient());
        String serializedUser = createSerializedValidUser();

        Response response = authenticatedPut(USERS_ROUTE, serializedUser);

        assertStatusCode(response, Status.OK);
    }

    @Test
    public void givenAnUnauthenticatedUser_whenUpdateClient_thenReturnsUnauthorized() {
        String serializedUser = createSerializedValidUser();

        Response response = unauthenticatedPut(USERS_ROUTE, serializedUser);

        assertStatusCode(response, Status.UNAUTHORIZED);
    }

    @Test
    public void givenAUserWithAnEmptyPassword_whenCreatingUser_thenReturnsBadRequest() {
        String serializedUser = createSerializedUserWithEmptyPassword();

        Response response = unauthenticatedPost(USERS_ROUTE, serializedUser);

        assertStatusCode(response, Status.BAD_REQUEST);
    }

    @Test
    public void givenAUserWithAnEmptyPassword_whenUpdatingClient_thenReturnsUnauthorized() {
        authenticateAs(createSerializedClient());
        String serializedUser = createSerializedUserWithEmptyPassword();

        Response response = authenticatedPut(USERS_ROUTE, serializedUser);

        assertStatusCode(response, Status.BAD_REQUEST);
    }

    @Test
    public void givenAUserWithAnInvalidEmail_whenUpdatingClient_thenReturnsBadRequest() {
        authenticateAs(createSerializedClient());
        String serializedUser = createSerializedUserWithInvalidEmail();

        Response response = authenticatedPut(USERS_ROUTE, serializedUser);

        assertStatusCode(response, Status.BAD_REQUEST);
    }

    private static String createSerializedValidUser() {
        UserDto user = new UserDto();
        user.setUsername(RandomStringUtils.randomAlphabetic(20));
        user.setPassword(A_VALID_PASSWORD);
        user.setEmail(A_VALID_EMAIL);
        Gson gson = new Gson();

        return gson.toJson(user);
    }

    private String createSerializedUserWithInvalidName() {
        return createSerializedUser(
            AN_INVALID_NAME,
            A_VALID_PASSWORD,
            A_VALID_EMAIL
        );
    }

    private String createSerializedClient() {
        commonUser = new CommonUser();
        return createSerializedUser(
                commonUser.getUpdatingClientName(),
                commonUser.getUpdatingClientPassword(),
                commonUser.getUpdatingClientEmail()
        );
    }

    private String createSerializedUserWithInvalidEmail() {
        return createSerializedUser(
            generateRandomWord(),
            A_VALID_PASSWORD,
            AN_INVALID_EMAIL
        );
    }

    private String createSerializedUserWithEmptyPassword() {
        return createSerializedUser(
            generateRandomWord(),
            AN_EMPTY_PASSWORD,
            A_VALID_EMAIL
        );
    }
}
