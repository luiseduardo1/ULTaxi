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

@RunWith(MockitoJUnitRunner.class)
public class DriverResourceIT extends IntegrationTest {

    private static final String A_VALID_PASSWORD = "hunter2";
    private static final String A_VALID_SOCIAL_INSURANCE_NUMBER = "450050687";
    private static final String ANOTHER_VALID_SOCIAL_INSURANCE_NUMBER = "046454286";
    private static final String A_VALID_PHONE_NUMBER = "2342355678";
    private static final String A_VALID_NAME = "Freddy";
    private static final String A_VALID_LAST_NAME = "Mercury";
    private static final String A_SEARCH_PARAMETER = "first-name";

    @Test
    public void givenAuthenticatedAdmin_whenCreatingADriver_thenReturnsOk() {
        authenticateAs(Role.ADMINISTRATOR);

        Response response = authenticatedPost(DRIVERS_ROUTE, createSerializedValidDriver());

        assertStatusCode(response, Status.OK);
    }

    @Test
    public void givenAuthenticatedAdmin_whenSearchingForADriver_thenReturnsOk() {
        authenticateAs(Role.ADMINISTRATOR);
        authenticatedPost(DRIVERS_ROUTE, createSerializedValidDriver(ANOTHER_VALID_SOCIAL_INSURANCE_NUMBER));

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

    private String createSerializedValidDriver(String socialInsuranceNumber) {
        return createSerializedDriver(
            generateRandomWord(),
            A_VALID_PASSWORD,
            socialInsuranceNumber,
            A_VALID_PHONE_NUMBER,
            A_VALID_NAME,
            A_VALID_LAST_NAME
        );
    }

    private String createSerializedValidDriver() {
        return createSerializedValidDriver(A_VALID_SOCIAL_INSURANCE_NUMBER);
    }
}
