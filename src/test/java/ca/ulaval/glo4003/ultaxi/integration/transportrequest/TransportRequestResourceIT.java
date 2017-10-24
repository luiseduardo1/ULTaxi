package ca.ulaval.glo4003.ultaxi.integration.transportrequest;

import ca.ulaval.glo4003.ultaxi.domain.user.Role;
import ca.ulaval.glo4003.ultaxi.integration.IntegrationTest;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ws.rs.core.Response.Status;


@RunWith(MockitoJUnitRunner.class)
public class TransportRequestResourceIT extends IntegrationTest {

    private static final String A_VALID_VEHICLE_TYPE = "CAR";
    private static final String A_VALID_NOTE = "Note";
    private static final double A_VALID_LATITUDE = 45.12321;
    private static final double A_VALID_LONGITUDE = 15.34344;
    private static final String AN_INVALID_VEHICLE_TYPE = "Invalid";
    private static final double AN_INVALID_LATITUDE = -145.12321;
    private static final double AN_INVALID_LONGITUDE = 235.34344;

    @Before
    public void setUp() {
        authenticateAs(Role.CLIENT);
    }

    @Test
    public void givenAValidTransportRequest_whenSendRequest_thenRequestIsCreated() {
        String serializedTransportRequest = createSerializedValidTransportRequest();

        Response response = authenticatedPost(TRANSPORT_REQUEST_ROUTE, serializedTransportRequest);

        assertStatusCode(response, Status.CREATED);
    }

    @Test
    public void givenATransportRequestWithInvalidLatitude_whenSendRequest_thenReturnsBadRequest() {
        String serializedTransportRequest = createSerializedTransportRequestWithInvalidLatitude();

        Response response = authenticatedPost(TRANSPORT_REQUEST_ROUTE, serializedTransportRequest);

        assertStatusCode(response, Status.BAD_REQUEST);
    }

    @Test
    public void givenATransportRequestWithInvalidLongitude_whenSendRequest_thenReturnsBadRequest() {
        String serializedTransportRequest = createSerializedTransportRequestWithInvalidLongitude();

        Response response = authenticatedPost(TRANSPORT_REQUEST_ROUTE, serializedTransportRequest);

        assertStatusCode(response, Status.BAD_REQUEST);
    }

    @Test
    public void givenATransportRequestWithInvalidVehicleType_whenSendRequest_thenReturnsBadRequest() {
        String serializedTransportRequest = createSerializedTransportRequestWithInvalidVehicleType();

        Response response = authenticatedPost(TRANSPORT_REQUEST_ROUTE, serializedTransportRequest);

        assertStatusCode(response, Status.BAD_REQUEST);
    }

    @Test
    public void givenAnUnauthenticatedRequest_whenSendRequest_thenReturnsUnauthorized() {
        String serializedTransportRequest = createSerializedValidTransportRequest();

        Response response = unauthenticatedPost(TRANSPORT_REQUEST_ROUTE, serializedTransportRequest);

        assertStatusCode(response, Status.UNAUTHORIZED);
    }

    private String createSerializedValidTransportRequest() {
        return createSerializedTransportRequest(
            A_VALID_VEHICLE_TYPE,
            A_VALID_NOTE,
            A_VALID_LATITUDE,
            A_VALID_LONGITUDE
        );
    }

    private String createSerializedTransportRequestWithInvalidLatitude() {
        return createSerializedTransportRequest(
            A_VALID_VEHICLE_TYPE,
            A_VALID_NOTE,
            AN_INVALID_LATITUDE,
            A_VALID_LONGITUDE
        );
    }

    private String createSerializedTransportRequestWithInvalidLongitude() {
        return createSerializedTransportRequest(
            A_VALID_VEHICLE_TYPE,
            A_VALID_NOTE,
            A_VALID_LATITUDE,
            AN_INVALID_LONGITUDE
        );
    }

    private String createSerializedTransportRequestWithInvalidVehicleType() {
        return createSerializedTransportRequest(
            AN_INVALID_VEHICLE_TYPE,
            A_VALID_NOTE,
            A_VALID_LATITUDE,
            A_VALID_LONGITUDE
        );
    }
}
