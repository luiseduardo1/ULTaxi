package ca.ulaval.glo4003.ultaxi.integration.vehicle;

import ca.ulaval.glo4003.ultaxi.domain.vehicle.VehicleType;
import ca.ulaval.glo4003.ultaxi.integration.IntegrationTest;
import ca.ulaval.glo4003.ultaxi.transfer.vehicle.VehicleDto;
import io.restassured.response.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ws.rs.core.Response.Status;

@RunWith(MockitoJUnitRunner.class)
public class VehicleResourceIT extends IntegrationTest {

    private static final String VEHICLES_ROUTE = "/api/vehicles";

    private static final String A_VALID_TYPE = VehicleType.Car.name();
    private static final String AN_INVALID_TYPE = null;
    private static final String A_VALID_COLOR = "Dark Red";
    private static final String A_VALID_MODEL = "Nissan Sentra";
    private static final String A_VALID_REGISTRATION_NUMBER = "T68688";
    private static final String ANOTHER_VALID_REGISTRATION_NUMBER = "T99999";

    @Test
    public void givenVehicleWithValidType_whenCreateVehicle_thenVehicleIsCreated() {
        String serializedVehicle = createSerializedValidVehicle(A_VALID_REGISTRATION_NUMBER);

        Response response = unauthenticatedPost(VEHICLES_ROUTE, serializedVehicle);

        assertStatusCode(response, Status.OK);
    }

    @Test
    public void givenAlreadyExistingVehicle_whenCreateVehicle_thenReturnsBadRequest() {
        String serializedVehicle = createSerializedValidVehicle(ANOTHER_VALID_REGISTRATION_NUMBER);
        unauthenticatedPost(VEHICLES_ROUTE, serializedVehicle);

        Response response = unauthenticatedPost(VEHICLES_ROUTE, serializedVehicle);

        assertStatusCode(response, Status.BAD_REQUEST);
    }

    @Test
    public void givenVehicleWithInvalidType_whenCreateVehicle_thenReturnsBadRequest() {
        String serializedVehicleWithInvalidType = createSerializedVehicleWithInvalidType(
            A_VALID_REGISTRATION_NUMBER
        );

        Response response = unauthenticatedPost(VEHICLES_ROUTE, serializedVehicleWithInvalidType);

        assertStatusCode(response, Status.BAD_REQUEST);
    }

    private String createSerializedValidVehicle(String registrationNumber) {
        return createVehicleJSON(A_VALID_TYPE, A_VALID_COLOR, A_VALID_MODEL, registrationNumber);
    }

    private String createSerializedVehicleWithInvalidType(String registrationNumber) {
        return createVehicleJSON(AN_INVALID_TYPE, A_VALID_COLOR, A_VALID_MODEL, registrationNumber);
    }

    private String createVehicleJSON(String type, String color, String model, String registrationNumber) {
        VehicleDto vehicleDto = new VehicleDto();
        vehicleDto.setType(type);
        vehicleDto.setColor(color);
        vehicleDto.setModel(model);
        vehicleDto.setRegistrationNumber(registrationNumber);

        return serializeDto(vehicleDto);
    }
}
