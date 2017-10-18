package ca.ulaval.glo4003.ultaxi.integration.user.driver;

import static io.restassured.RestAssured.given;

import ca.ulaval.glo4003.ultaxi.transfer.user.driver.DriverDto;
import com.google.gson.Gson;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ws.rs.core.Response;

@RunWith(MockitoJUnitRunner.class)
public class DriverResourceIT {

    private static final String API_DRIVERS = "/api/drivers";
    private static final String A_VALID_SIN = "972487086";
    private static final String A_VALID_PASSWORD = "hunter2";
    private static final String A_VALID_PHONE_NUMBER = "2342355678";
    private static final String A_VALID_NAME = "Freddy";
    private static final String A_VALID_LAST_NAME = "Mercury";

    private String aValidUsername;

    @Before
    public void setUp() throws Exception {
        aValidUsername = RandomStringUtils.randomAlphabetic(25);
    }

    @Test
    public void givenUnauthenticatedAdministrator_whenCreateADriver_thenReturnsUnauthorized() {
        givenBaseServer()
            .body(givenDriver())
            .when()
            .post(API_DRIVERS)
            .then()
            .statusCode(Response.Status.UNAUTHORIZED.getStatusCode());
    }

    @Test
    public void givenUnauthenticatedAdministrator_whenSearchingForDrivers_thenReturnsUnauthorized() {
        givenBaseServer()
            .queryParam("first-name", A_VALID_NAME)
            .when()
            .get(API_DRIVERS)
            .then()
            .statusCode(Response.Status.UNAUTHORIZED.getStatusCode());
    }

    private RequestSpecification givenBaseServer() {
        return given()
            .accept(ContentType.JSON)
            .contentType(ContentType.JSON);
    }

    private String givenDriver() {
        DriverDto driverDto = new DriverDto();
        driverDto.setSin(A_VALID_SIN);
        driverDto.setUsername(aValidUsername);
        driverDto.setPhoneNumber(A_VALID_PHONE_NUMBER);
        driverDto.setName(A_VALID_NAME);
        driverDto.setLastName(A_VALID_LAST_NAME);
        driverDto.setPassword(A_VALID_PASSWORD);
        Gson gson = new Gson();

        return gson.toJson(driverDto);
    }
}
