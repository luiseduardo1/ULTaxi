package ca.ulaval.glo4003.ultaxi.integration.transportrequest;

import ca.ulaval.glo4003.ultaxi.domain.user.Role;
import ca.ulaval.glo4003.ultaxi.integration.IntegrationTest;
import ca.ulaval.glo4003.ultaxi.transfer.user.UserDto;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ws.rs.core.Response.Status;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@RunWith(MockitoJUnitRunner.class)
public class TransportRequestResourceIT extends IntegrationTest {

    private static final String A_VALID_VEHICLE_TYPE = "CAR";
    private static final String A_VALID_NOTE = "Note";
    private static final double A_VALID_LATITUDE = 45.12321;
    private static final double A_VALID_LONGITUDE = 15.34344;
    private static final String AN_INVALID_VEHICLE_TYPE = "Invalid";
    private static final double AN_INVALID_LATITUDE = -145.12321;
    private static final double AN_INVALID_LONGITUDE = 235.34344;
    private static final String A_VALID_TRANSPORT_REQUEST_ID = "1";
    private static final String AN_INVALID_TRANSPORT_REQUEST_ID = "2";
    private static final String A_CLIENT = "1";
    private static final String A_DRIVER = "1";
    private static final String AN_ADMINISTRATOR_INDEX = "1";
    private static final String A_VALID_PASSWORD = "password";
    private static final String A_VALID_SOCIAL_INSURANCE_NUMBER = "130692544";
    private static final String A_VALID_PHONE_NUMBER = "2342355679";
    private static final String A_USERNAME = "Karlee";
    private static final String A_VALID_EMAIL = "Karlee@mail.com";
    private static final String A_VALID_NAME = "Karl";
    private static final String A_VALID_LAST_NAME = "Max";


    @Before
    public void setUp() {
    }

    @Test
    public void givenAValidTransportRequest_whenSendRequest_thenRequestIsCreated() {
        authenticateAs(Role.CLIENT, A_CLIENT);
        String serializedTransportRequest = createSerializedValidTransportRequest();

        Response response = authenticatedPost(TRANSPORT_REQUEST_ROUTE, serializedTransportRequest);

        assertStatusCode(response, Status.CREATED);
    }

    @Test
    public void givenATransportRequestWithInvalidLatitude_whenSendRequest_thenReturnsBadRequest() {
        authenticateAs(Role.CLIENT, A_CLIENT);
        String serializedTransportRequest = createSerializedTransportRequestWithInvalidLatitude();

        Response response = authenticatedPost(TRANSPORT_REQUEST_ROUTE, serializedTransportRequest);

        assertStatusCode(response, Status.BAD_REQUEST);
    }

    @Test
    public void givenATransportRequestWithInvalidLongitude_whenSendRequest_thenReturnsBadRequest() {
        authenticateAs(Role.CLIENT, A_CLIENT);
        String serializedTransportRequest = createSerializedTransportRequestWithInvalidLongitude();

        Response response = authenticatedPost(TRANSPORT_REQUEST_ROUTE, serializedTransportRequest);

        assertStatusCode(response, Status.BAD_REQUEST);
    }

    @Test
    public void givenATransportRequestWithInvalidVehicleType_whenSendRequest_thenReturnsBadRequest() {
        authenticateAs(Role.CLIENT, A_CLIENT);
        String serializedTransportRequest = createSerializedTransportRequestWithInvalidVehicleType();

        Response response = authenticatedPost(TRANSPORT_REQUEST_ROUTE, serializedTransportRequest);

        assertStatusCode(response, Status.BAD_REQUEST);
    }

    @Test
    public void givenADriverWithNoTransportRequestAssigned_whenNotifyHasArrived_thenReturnsBadRequest() {
        String aDriverWithoutTransportRequest = createSerializedDriverWithoutAssignedTransportRequest();
        authenticateAs(aDriverWithoutTransportRequest);

        Response response = authenticatedPost(DRIVER_HAS_ARRIVED_NOTIFICATION);

        assertStatusCode(response, Status.BAD_REQUEST);
    }

    @Test
    public void givenAnUnauthenticatedClient_whenSendRequest_thenReturnsUnauthorized() {
        String serializedTransportRequest = createSerializedValidTransportRequest();

        Response response = unauthenticatedPost(TRANSPORT_REQUEST_ROUTE, serializedTransportRequest);

        assertStatusCode(response, Status.UNAUTHORIZED);
    }

    @Test
    public void givenAValidTransportRequestId_whenAssignTransportRequestId_thenReturnsIsOk(){
        authenticateAs(Role.DRIVER, A_DRIVER);
        String transportRequestId = A_VALID_TRANSPORT_REQUEST_ID;

        Response response = authenticatedPost(ASSIGN_TRANSPORT_REQUEST_ROUTE, transportRequestId);

        assertStatusCode(response, Status.OK);
    }

    @Test
    public void givenAnUnauthenticatedDriver_whenAssignTransportRequest_thenReturnsUnauthorized() {
        authenticateAs(Role.DRIVER, A_DRIVER);
        String transportRequestId = A_VALID_TRANSPORT_REQUEST_ID;

        Response response = unauthenticatedPost(ASSIGN_TRANSPORT_REQUEST_ROUTE, transportRequestId);

        assertStatusCode(response, Status.UNAUTHORIZED);
    }

    @Test
    public void givenAnInvalidTransportRequestId_whenAssignTransportRequest_thenReturnsBadRequest(){
        authenticateAs(Role.DRIVER, A_DRIVER);
        String transportRequestId = AN_INVALID_TRANSPORT_REQUEST_ID;

        Response response = authenticatedPost(ASSIGN_TRANSPORT_REQUEST_ROUTE, transportRequestId);

        assertStatusCode(response, Status.BAD_REQUEST);
    }

    @Test
    public void givenAnAuthenticatedClient_whenAssignTransportRequest_thenReturnsForbidden(){
        authenticateAs(Role.CLIENT, A_CLIENT);
        String transportRequestId = A_VALID_TRANSPORT_REQUEST_ID;

        Response response = authenticatedPost(ASSIGN_TRANSPORT_REQUEST_ROUTE, transportRequestId);

        assertStatusCode(response, Status.FORBIDDEN);
    }

    private String createSerializedDriverWithoutAssignedTransportRequest() {
        authenticateAs(Role.ADMINISTRATOR, AN_ADMINISTRATOR_INDEX);
        authenticatedPost(DRIVERS_ROUTE, createSerializedDriver(
            A_USERNAME,
            A_VALID_PASSWORD,
            A_VALID_SOCIAL_INSURANCE_NUMBER,
            A_VALID_PHONE_NUMBER,
            A_VALID_NAME,
            A_VALID_LAST_NAME,
            A_VALID_EMAIL
        ));

        return createSerializedUser(A_USERNAME, A_VALID_PASSWORD, A_VALID_EMAIL);
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
