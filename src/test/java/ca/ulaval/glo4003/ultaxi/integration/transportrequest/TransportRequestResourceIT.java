package ca.ulaval.glo4003.ultaxi.integration.transportrequest;

import ca.ulaval.glo4003.ultaxi.domain.user.Role;
import ca.ulaval.glo4003.ultaxi.integration.IntegrationTest;
import ca.ulaval.glo4003.ultaxi.transfer.transportrequest.TransportRequestDto;
import com.google.gson.Gson;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ws.rs.core.Response.Status;


@RunWith(MockitoJUnitRunner.class)
public class TransportRequestResourceIT extends IntegrationTest {

    private static final String TRANSPORT_REQUEST_ROUTE = "/api/transportRequest";
    private static final String A_VALID_NOTE = "Note";
    private static final String A_VALID_VEHICLE_TYPE = "Car";
    private static final String AN_INVALID_VEHICLE_TYPE = "Invalid";
    private static final double A_VALID_LATITUDE = 45.12321;
    private static final double A_VALID_LONGITUDE = 15.34344;
    private static final double AN_INVALID_LATITUDE = -145.12321;
    private static final double AN_INVALID_LONGITUDE = 235.34344;

    @Before
    public void setUp() {
        authenticateAs(Role.Client);
    }

    @Test
    public void givenATransportRequest_whenSendRequest_thenRequestIsCreated() {
        String serializedTransportRequest = givenAValidTransportRequest();

        Response response = authenticatedPost(TRANSPORT_REQUEST_ROUTE, serializedTransportRequest);

        assertStatusCode(response, Status.CREATED);
    }

    @Test
    public void givenATransportRequestWithInvalidLatitude_whenSendRequest_thenReturnsBadRequest() {
        String serializedTransportRequest = givenATransportRequestWithInvalidLatitude();

        Response response = authenticatedPost(TRANSPORT_REQUEST_ROUTE, serializedTransportRequest);

        assertStatusCode(response, Status.BAD_REQUEST);
    }

    @Test
    public void givenATransportRequestWithInvalidLongitude_whenSendRequest_thenReturnsBadRequest() {
        String serializedTransportRequest = givenATransportRequestWithInvalidLongitude();

        Response response = authenticatedPost(TRANSPORT_REQUEST_ROUTE, serializedTransportRequest);

        assertStatusCode(response, Status.BAD_REQUEST);
    }

    @Test
    public void givenATransportRequestWithInvalidVehicleType_whenSendRequest_thenReturnsBadRequest() {
        String serializedTransportRequest = givenATransportRequestWithInvalidVehicleType();

        Response response = authenticatedPost(TRANSPORT_REQUEST_ROUTE, serializedTransportRequest);

        assertStatusCode(response, Status.BAD_REQUEST);
    }

    @Test
    public void givenAnUnauthenticatedRequest_whenSendRequest_thenReturnsUnauthorized() {
        String serializedTransportRequest = givenAValidTransportRequest();

        Response response = unauthenticatedPost(TRANSPORT_REQUEST_ROUTE, serializedTransportRequest);

        assertStatusCode(response, Status.UNAUTHORIZED);
    }

    private String givenAValidTransportRequest() {
        return createSerializedTransportRequest(A_VALID_VEHICLE_TYPE, A_VALID_NOTE, A_VALID_LATITUDE, A_VALID_LONGITUDE);
    }

    private String givenATransportRequestWithInvalidLatitude() {
        return createSerializedTransportRequest(A_VALID_VEHICLE_TYPE, A_VALID_NOTE, AN_INVALID_LATITUDE,
                                                A_VALID_LONGITUDE);
    }

    private String givenATransportRequestWithInvalidLongitude() {
        return createSerializedTransportRequest(A_VALID_VEHICLE_TYPE, A_VALID_NOTE, A_VALID_LATITUDE,
                                                AN_INVALID_LONGITUDE);
    }

    private String givenATransportRequestWithInvalidVehicleType() {
        return createSerializedTransportRequest(AN_INVALID_VEHICLE_TYPE, A_VALID_NOTE, A_VALID_LATITUDE,
                                                AN_INVALID_LONGITUDE);
    }

    private String createSerializedTransportRequest(String vehicleType, String note, double latitude, double longitude) {
        TransportRequestDto transportRequestDto = new TransportRequestDto();
        transportRequestDto.setVehicleType(vehicleType);
        transportRequestDto.setNote(note);
        transportRequestDto.setLatitude(latitude);
        transportRequestDto.setLongitude(longitude);

        return serializeDto(transportRequestDto);
    }
}
