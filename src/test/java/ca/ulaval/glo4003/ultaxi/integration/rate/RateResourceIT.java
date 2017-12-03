package ca.ulaval.glo4003.ultaxi.integration.rate;

import ca.ulaval.glo4003.ultaxi.domain.user.Role;
import ca.ulaval.glo4003.ultaxi.domain.vehicle.VehicleType;
import ca.ulaval.glo4003.ultaxi.integration.IntegrationTest;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ws.rs.core.Response.Status;
import java.math.BigDecimal;

@RunWith(MockitoJUnitRunner.class)
public class RateResourceIT extends IntegrationTest {

    private static final BigDecimal AN_INVALID_RATE = BigDecimal.ZERO;
    private static final BigDecimal A_VALID_RATE = BigDecimal.TEN;
    private static final String A_VALID_VEHICLE_TYPE = VehicleType.CAR.name();
    private static final String A_SECOND_VALID_VEHICLE_TYPE = VehicleType.VAN.name();

    @Before
    public void setUp() {
        authenticateAs(Role.ADMINISTRATOR);
    }

    @Test
    public void givenAuthenticatedAdmin_whenCreateDistanceRate_thenReturnsOk() {
        String serializedDistanceRate = createSerializedValidDistanceRate(A_VALID_VEHICLE_TYPE);
        Response response = authenticatedPost(RATES_ROUTE, serializedDistanceRate);

        assertStatusCode(response, Status.OK);
    }

    @Test
    public void givenDistanceRateWithInvalidRate_whenCreateRate_thenReturnsBadRequest() {
        String serializedDistanceRate = createSerializedDistanceWithInvalidRate(A_SECOND_VALID_VEHICLE_TYPE);
        Response response = authenticatedPost(RATES_ROUTE, serializedDistanceRate);

        assertStatusCode(response, Status.BAD_REQUEST);
    }

    @Test
    public void givenAlreadyExistingDistanceRate_whenUpdateRate_thenReturnsOk() {
        String serializedDistanceRate = createSerializedValidDistanceRate(A_VALID_VEHICLE_TYPE);
        Response response = authenticatedPut(RATES_ROUTE, serializedDistanceRate);

        assertStatusCode(response, Status.OK);
    }

    @Test
    public void givenAlreadyExistingDistanceRateWithInvalidRate_whenUpdateRate_thenReturnsBadRequest() {
        String serializedDistanceRate = createSerializedDistanceWithInvalidRate(A_SECOND_VALID_VEHICLE_TYPE);
        Response response = authenticatedPut(RATES_ROUTE, serializedDistanceRate);

        assertStatusCode(response, Status.BAD_REQUEST);
    }

    private String createSerializedValidDistanceRate(String vehicleType) {
        return createSerializedDistanceRate(
            vehicleType,
            A_VALID_RATE
        );
    }

    private String createSerializedDistanceWithInvalidRate(String vehicleType) {
        return createSerializedDistanceRate(
            vehicleType,
            AN_INVALID_RATE
        );
    }
}
