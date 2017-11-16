package ca.ulaval.glo4003.ultaxi.integration.user.driver;

import ca.ulaval.glo4003.ultaxi.domain.user.Role;
import ca.ulaval.glo4003.ultaxi.integration.IntegrationTest;
import io.restassured.response.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ws.rs.core.Response.Status;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RunWith(MockitoJUnitRunner.class)
public class DriverResourceIT extends IntegrationTest {

    private static final String A_VALID_PASSWORD = "hunter2";
    private static final String AN_EXISTING_SOCIAL_INSURANCE_NUMBER = "972487086";
    private static final String A_THIRD_VALID_SOCIAL_INSURANCE_NUMBER = "293235040";
    private static final String AN_INVALID_SOCIAL_INSURANCE_NUMBER = "9724870865";
    private static final String AN_EXISTING_USERNAME = "driverUsername";
    private static final String AN_INVALID_USERNAME = "invalid@invalid.com";
    private static final String A_VALID_SOCIAL_INSURANCE_NUMBER = "450050687";
    private static final String ANOTHER_VALID_SOCIAL_INSURANCE_NUMBER = "046454286";
    private static final String A_VALID_PHONE_NUMBER = "2342355678";
    private static final String AN_INVALID_PHONE_NUMBER = "3141592653";
    private static final String A_VALID_NAME = "Freddy";
    private static final String A_VALID_LAST_NAME = "Mercury";
    private static final String A_SEARCH_PARAMETER = "first-name";
    private static final String A_ADMINISTRATOR = "1";

    @Test
    public void givenAuthenticatedAdmin_whenCreatingADriver_thenReturnsOk() {
        authenticateAs(Role.ADMINISTRATOR, A_ADMINISTRATOR);

        Response response = authenticatedPost(DRIVERS_ROUTE, createSerializedValidDriver());

        assertStatusCode(response, Status.OK);
    }

    @Test
    public void givenDriverWithInvalidSocialInsuranceNumber_whenCreateDriver_thenReturnsBadRequest() {
        authenticateAs(Role.ADMINISTRATOR, A_ADMINISTRATOR);

        Response response = authenticatedPost(DRIVERS_ROUTE, createSerializedDriverWithInvalidSocialInsuranceNumber());

        assertStatusCode(response, Status.BAD_REQUEST);
    }

    @Test
    public void givenValidSearchQueryWithNoAssociatedDriver_whenSearching_thenReturnsNotFound() {
        authenticateAs(Role.ADMINISTRATOR, A_ADMINISTRATOR);

        Map<String, String> queryParameters = new HashMap<>();
        queryParameters.put("last-name", UUID.randomUUID().toString());

        Response response = authenticatedGet(DRIVERS_ROUTE, queryParameters);

        assertStatusCode(response, Status.NOT_FOUND);
    }

    @Test
    public void givenADriverWithAlreadyExistingSocialInsuranceNumber_whenCreatingDriver_thenReturnsBadRequest() {
        authenticateAs(Role.ADMINISTRATOR, A_ADMINISTRATOR);

        Response response = authenticatedPost(DRIVERS_ROUTE, createSerializedDriverWithExistingSocialInsuranceNumber());

        assertStatusCode(response, Status.BAD_REQUEST);
    }

    @Test
    public void givenDriverWithInvalidUsername_whenCreateDriver_thenReturnsBadRequest() {
        authenticateAs(Role.ADMINISTRATOR, A_ADMINISTRATOR);

        Response response = authenticatedPost(DRIVERS_ROUTE, createSerializedDriverWithInvalidUsername());

        assertStatusCode(response, Status.BAD_REQUEST);
    }

    @Test
    public void givenDriverWithInvalidPhoneNumber_whenCreateDriver_thenReturnsBadRequest() {
        authenticateAs(Role.ADMINISTRATOR, A_ADMINISTRATOR);

        Response response = authenticatedPost(DRIVERS_ROUTE, createSerializedDriverWithInvalidPhoneNumber());

        assertStatusCode(response, Status.BAD_REQUEST);
    }

    @Test
    public void givenAuthenticatedAdmin_whenSearchingForADriver_thenReturnsOk() {
        authenticateAs(Role.ADMINISTRATOR, A_ADMINISTRATOR);
        authenticatedPost(DRIVERS_ROUTE, createSerializedDriver(ANOTHER_VALID_SOCIAL_INSURANCE_NUMBER));

        Map<String, String> queryParameters = new HashMap<>();
        queryParameters.put("last-name", A_VALID_LAST_NAME);
        Response response = authenticatedGet(DRIVERS_ROUTE, queryParameters);

        assertStatusCode(response, Status.OK);
    }

    @Test
    public void givenUnauthenticatedUser_whenCreatingADriver_thenReturnsUnauthorized() {
        String serializedDriver = createSerializedValidDriver();

        Response response = unauthenticatedPost(DRIVERS_ROUTE, serializedDriver);

        assertStatusCode(response, Status.UNAUTHORIZED);
    }

    @Test
    public void givenUnauthenticatedUser_whenSearchingForDrivers_thenReturnsUnauthorized() {
        Response response = unauthenticatedGet(
            DRIVERS_ROUTE,
            createSearchQueryParameter(A_SEARCH_PARAMETER, A_VALID_NAME)
        );

        assertStatusCode(response, Status.UNAUTHORIZED);
    }

    private String createSerializedDriverWithInvalidSocialInsuranceNumber() {
        return createSerializedDriver(AN_INVALID_SOCIAL_INSURANCE_NUMBER);
    }

    private String createSerializedDriverWithExistingSocialInsuranceNumber() {
        return createSerializedDriver(AN_EXISTING_SOCIAL_INSURANCE_NUMBER);
    }

    private String createSerializedExistingDriver() {
        return createSerializedDriver(
            AN_EXISTING_USERNAME,
            A_VALID_PASSWORD,
            A_THIRD_VALID_SOCIAL_INSURANCE_NUMBER,
            A_VALID_PHONE_NUMBER,
            A_VALID_NAME,
            A_VALID_LAST_NAME
        );
    }

    private String createSerializedDriver(String socialInsuranceNumber) {
        return createSerializedDriver(
            generateRandomWord(),
            A_VALID_PASSWORD,
            socialInsuranceNumber,
            A_VALID_PHONE_NUMBER,
            A_VALID_NAME,
            A_VALID_LAST_NAME
        );
    }

    private String createSerializedDriverWithInvalidUsername() {
        return createSerializedDriver(
            AN_INVALID_USERNAME,
            A_VALID_PASSWORD,
            A_THIRD_VALID_SOCIAL_INSURANCE_NUMBER,
            A_VALID_PHONE_NUMBER,
            A_VALID_NAME,
            A_VALID_LAST_NAME
        );
    }

    private String createSerializedDriverWithInvalidPhoneNumber() {
        return createSerializedDriver(
            generateRandomWord(),
            A_VALID_PASSWORD,
            A_THIRD_VALID_SOCIAL_INSURANCE_NUMBER,
            AN_INVALID_PHONE_NUMBER,
            A_VALID_NAME,
            A_VALID_LAST_NAME
        );
    }

    private String createSerializedValidDriver() {
        return createSerializedDriver(A_VALID_SOCIAL_INSURANCE_NUMBER);
    }
}
