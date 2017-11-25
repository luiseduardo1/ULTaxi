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
public class RateResourceIT extends IntegrationTest{

    private static final BigDecimal AN_INVALID_RATE = BigDecimal.ZERO;
    private static final BigDecimal A_VALID_RATE = BigDecimal.TEN;
    private static final String A_VALID_VEHICLE_TYPE = VehicleType.CAR.name();

    @Before
    public void setUp() {
        authenticateAs(Role.ADMINISTRATOR);
    }

    @Test
    public void givenUnauthenticatedUser_whenCreateDistanceRate_thenReturnsUnauthorized() {
        String serializedDistanceRate = createSerializedValidDistanceRate();

        Response response = authenticatedPost(RATES_ROUTE, serializedDistanceRate);

        assertStatusCode(response, Status.UNAUTHORIZED);
    }

    private String createSerializedValidDistanceRate() {
        return createSerializedDistanceRate(
                A_VALID_VEHICLE_TYPE,
                A_VALID_RATE
        );
    }

    private String createSerializedDistanceWithInvalidRate() {
        return createSerializedDistanceRate(
                A_VALID_VEHICLE_TYPE,
                AN_INVALID_RATE
        );
    }
}
