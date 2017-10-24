package ca.ulaval.glo4003.ultaxi.integration.vehicle;

import ca.ulaval.glo4003.ultaxi.domain.vehicle.VehicleType;
import ca.ulaval.glo4003.ultaxi.integration.IntegrationTest;
import io.restassured.response.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ws.rs.core.Response.Status;

@RunWith(MockitoJUnitRunner.class)
public class VehicleResourceIT extends IntegrationTest {

    private static final String A_VALID_TYPE = VehicleType.Car.name();
    private static final String A_VALID_COLOR = "Dark Red";
    private static final String A_VALID_MODEL = "Nissan Sentra";
    private static final String AN_INVALID_TYPE = null;

    @Test
    public void givenVehicleWithValidType_whenCreateVehicle_thenVehicleIsCreated() {
        String serializedVehicle = createSerializedValidVehicle();

        Response response = unauthenticatedPost(VEHICLES_ROUTE, serializedVehicle);

        assertStatusCode(response, Status.OK);
    }

    @Test
    public void givenAlreadyExistingVehicle_whenCreateVehicle_thenReturnsBadRequest() {
        String serializedVehicle = createSerializedValidVehicle();
        unauthenticatedPost(VEHICLES_ROUTE, serializedVehicle);

        Response response = unauthenticatedPost(VEHICLES_ROUTE, serializedVehicle);

        assertStatusCode(response, Status.BAD_REQUEST);
    }

    @Test
    public void givenVehicleWithInvalidType_whenCreateVehicle_thenReturnsBadRequest() {
        String serializedVehicle = createSerializedVehicleWithInvalidType();

        Response response = unauthenticatedPost(VEHICLES_ROUTE, serializedVehicle);

        assertStatusCode(response, Status.BAD_REQUEST);
    }

    private String createSerializedValidVehicle() {
        return createSerializedVehicle(
            A_VALID_TYPE,
            A_VALID_COLOR,
            A_VALID_MODEL,
            generateRandomWord()
        );
    }

    private String createSerializedVehicleWithInvalidType() {
        return createSerializedVehicle(
            AN_INVALID_TYPE,
            A_VALID_COLOR,
            A_VALID_MODEL,
            generateRandomWord()
        );
    }
}
