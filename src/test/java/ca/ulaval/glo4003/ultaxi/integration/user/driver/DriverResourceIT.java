package ca.ulaval.glo4003.ultaxi.integration.user.driver;

import ca.ulaval.glo4003.ultaxi.integration.IntegrationTest;
import ca.ulaval.glo4003.ultaxi.transfer.user.driver.DriverDto;
import com.google.gson.Gson;
import io.restassured.response.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ws.rs.core.Response.Status;

@RunWith(MockitoJUnitRunner.class)
public class DriverResourceIT extends IntegrationTest {

    private static final String DRIVERS_ROUTE = "/api/drivers";
    private static final String A_VALID_SOCIAL_INSURANCE_NUMBER = "972487086";
    private static final String A_VALID_PASSWORD = "hunter2";
    private static final String A_VALID_PHONE_NUMBER = "2342355678";
    private static final String A_VALID_NAME = "Freddy";
    private static final String A_VALID_LAST_NAME = "Mercury";
    private static final String A_SEARCH_PARAMETER = "first-name";

    @Test
    public void givenUnauthenticatedAdministrator_whenCreateADriver_thenReturnsUnauthorized() {
        String serializedDriver = createSerializedDriver();

        Response response = unauthenticatedPost(DRIVERS_ROUTE, serializedDriver);

        assertStatusCode(response, Status.UNAUTHORIZED);
    }

    @Test
    public void givenUnauthenticatedAdministrator_whenSearchingForDrivers_thenReturnsUnauthorized() {
        Response response = unauthenticatedGet(
            DRIVERS_ROUTE,
            createSearchQueryParameter(A_SEARCH_PARAMETER, A_VALID_NAME)
        );

        assertStatusCode(response, Status.UNAUTHORIZED);
    }

    private String createSerializedDriver() {
        DriverDto driverDto = new DriverDto();
        driverDto.setUsername(generateRandomWord());
        driverDto.setSocialInsuranceNumber(A_VALID_SOCIAL_INSURANCE_NUMBER);
        driverDto.setPhoneNumber(A_VALID_PHONE_NUMBER);
        driverDto.setName(A_VALID_NAME);
        driverDto.setLastName(A_VALID_LAST_NAME);
        driverDto.setPassword(A_VALID_PASSWORD);

        Gson gson = new Gson();
        return gson.toJson(driverDto);
    }
}
