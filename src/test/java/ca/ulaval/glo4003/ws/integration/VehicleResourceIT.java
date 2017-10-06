package ca.ulaval.glo4003.ws.integration;

import ca.ulaval.glo4003.ws.api.vehicle.dto.VehicleDto;
import com.google.gson.Gson;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ws.rs.core.Response;

import static io.restassured.RestAssured.given;

@RunWith(MockitoJUnitRunner.class)
public class VehicleResourceIT {

    private static final String VEHICLES_API = "/api/vehicles";

    private static final String A_VALID_TYPE = "Car";
    private static final String AN_INVALID_TYPE = "Dumpster Truck";
    private static final String A_VALID_COLOR = "Dark Red";
    private static final String A_VALID_MODEL = "Nissan Sentra";
    private static final String A_VALID_REGISTRATION_NUMBER = "T68688";
    private static final String ANOTHER_VALID_REGISTRATION_NUMBER = "T99999";

    @Test
    public void givenVehicleWithValidType_whenCreateVehicle_thenVehicleIsCreated() {
        givenBaseServer()
            .body(givenAValidVehicle(A_VALID_REGISTRATION_NUMBER))
            .when()
            .post(VEHICLES_API)
            .then()
            .statusCode(Response.Status.OK.getStatusCode());
    }

    @Test
    public void givenAlreadyExistingVehicle_whenCreateVehicle_thenReturnsBadRequest() {
        givenBaseServer()
            .body(givenAValidVehicle(ANOTHER_VALID_REGISTRATION_NUMBER))
            .when()
            .post(VEHICLES_API);

        givenBaseServer()
            .body(givenAValidVehicle(ANOTHER_VALID_REGISTRATION_NUMBER))
            .when()
            .post(VEHICLES_API)
            .then()
            .statusCode(Response.Status.BAD_REQUEST.getStatusCode());
    }

    @Test
    public void givenVehicleWithInvalidType_whenCreateVehicle_thenReturnsBadRequest() {
        givenBaseServer()
            .body(givenAVehicleWithInvalidType(A_VALID_REGISTRATION_NUMBER))
            .when()
            .post(VEHICLES_API)
            .then()
            .statusCode(Response.Status.BAD_REQUEST.getStatusCode());
    }

    private RequestSpecification givenBaseServer() {
        return given()
            .accept(ContentType.JSON)
            .contentType(ContentType.JSON);
    }

    private String givenAValidVehicle(String registrationNumber) {
        return createVehicleJSON(A_VALID_TYPE, A_VALID_COLOR, A_VALID_MODEL, registrationNumber);
    }

    private String givenAVehicleWithInvalidType(String registrationNumber) {
        return createVehicleJSON(AN_INVALID_TYPE, A_VALID_COLOR, A_VALID_MODEL, registrationNumber);
    }

    private String createVehicleJSON(String type, String color, String model, String registrationNumber) {
        VehicleDto vehicleDto = new VehicleDto();
        vehicleDto.setType(type);
        vehicleDto.setColor(color);
        vehicleDto.setModel(model);
        vehicleDto.setRegistrationNumber(registrationNumber);

        Gson gson = new Gson();
        return gson.toJson(vehicleDto);
    }
}
