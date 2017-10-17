package ca.ulaval.glo4003.ultaxi.integration.transportrequest;

import static io.restassured.RestAssured.given;

import ca.ulaval.glo4003.ultaxi.transfer.transportrequest.TransportRequestDto;
import ca.ulaval.glo4003.ultaxi.transfer.user.UserDto;
import ca.ulaval.glo4003.ultaxi.utils.StringUtil;
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
public class TransportRequestResourceIT {

    private static final String API_USERS = "/api/users";
    private static final String API_USER_AUTHENTICATION = String.format("%s/auth", API_USERS);
    private static final String SIGNIN_ROUTE = String.format("%s/signin", API_USER_AUTHENTICATION);
    private static final String REQUEST_API = "/api/transportRequest";
    private static final String A_VALID_NOTE = "Note";
    private static final String A_VALID_VEHICLE_TYPE = "Car";
    private static final String AN_INVALID_VEHICLE_TYPE = "Invalid";
    private static final double A_VALID_LATITUDE = 45.12321;
    private static final double A_VALID_LONGITUDE = 15.34344;
    private static final double AN_INVALID_LATITUDE = -145.12321;
    private static final double AN_INVALID_LONGITUDE = 235.34344;

    private String token;

    @Before
    public void setUp() {
        this.token = getToken();
    }

    @Test
    public void givenATransportRequest_whenSendRequest_thenRequestIsCreated() {
        givenBaseServer()
            .header("Authorization", token)
            .body(givenAValidTransportRequest())
            .when()
            .post(REQUEST_API)
            .then()
            .statusCode(Response.Status.CREATED.getStatusCode());
    }

    @Test
    public void givenATransportRequestWithInvalidLatitude_whenSendRequest_thenReturnsBadRequest() {
        givenBaseServer()
            .header("Authorization", token)
            .body(givenATransportRequestWithInvalidLatitude())
            .when()
            .post(REQUEST_API)
            .then()
            .statusCode(Response.Status.BAD_REQUEST.getStatusCode());
    }

    @Test
    public void givenATransportRequestWithInvalidLongitude_whenSendRequest_thenReturnsBadRequest() {
        givenBaseServer()
            .header("Authorization", token)
            .body(givenATransportRequestWithInvalidLongitude())
            .when()
            .post(REQUEST_API)
            .then()
            .statusCode(Response.Status.BAD_REQUEST.getStatusCode());
    }

    @Test
    public void givenATransportRequestWithInvalidVehicleType_whenSendRequest_thenReturnsBadRequest() {
        givenBaseServer()
            .header("Authorization", token)
            .body(givenATransportRequestWithInvalidVehicleType())
            .when()
            .post(REQUEST_API)
            .then()
            .statusCode(Response.Status.BAD_REQUEST.getStatusCode());
    }

    @Test
    public void givenAnUnauthenticatedRequest_whenSendRequest_thenReturnsUnauthorized() {
        givenBaseServer()
            .body(givenAValidTransportRequest())
            .when()
            .post(REQUEST_API)
            .then()
            .statusCode(Response.Status.UNAUTHORIZED.getStatusCode());
    }

    private RequestSpecification givenBaseServer() {
        return given()
            .accept(ContentType.JSON)
            .contentType(ContentType.JSON);
    }

    private String givenAValidTransportRequest() {
        return createTransportRequestJSON(A_VALID_VEHICLE_TYPE, A_VALID_NOTE, A_VALID_LATITUDE, A_VALID_LONGITUDE);
    }

    private String givenATransportRequestWithInvalidLatitude() {
        return createTransportRequestJSON(A_VALID_VEHICLE_TYPE, A_VALID_NOTE, AN_INVALID_LATITUDE,
                                          A_VALID_LONGITUDE);
    }

    private String givenATransportRequestWithInvalidLongitude() {
        return createTransportRequestJSON(A_VALID_VEHICLE_TYPE, A_VALID_NOTE, A_VALID_LATITUDE,
                                          AN_INVALID_LONGITUDE);
    }

    private String givenATransportRequestWithInvalidVehicleType() {
        return createTransportRequestJSON(AN_INVALID_VEHICLE_TYPE, A_VALID_NOTE, A_VALID_LATITUDE,
                                          AN_INVALID_LONGITUDE);
    }

    private String createTransportRequestJSON(String vehicleType, String note, double latitude, double longitude) {
        TransportRequestDto transportRequestDto = new TransportRequestDto();
        transportRequestDto.setVehicleType(vehicleType);
        transportRequestDto.setNote(note);
        transportRequestDto.setLatitude(latitude);
        transportRequestDto.setLongitude(longitude);
        Gson gson = new Gson();
        return gson.toJson(transportRequestDto);
    }

    private String givenUser() {
        UserDto userDto = new UserDto();
        userDto.setUserName("Bob");
        userDto.setPassword("Bob123");
        userDto.setEmail("bob@ulaval.ca");
        Gson gson = new Gson();
        return gson.toJson(userDto);
    }

    private String getToken() {
        String user = givenUser();
        givenBaseServer()
            .body(user)
            .when()
            .post(API_USERS);
        io.restassured.response.Response response = givenBaseServer()
            .body(user)
            .when()
            .post(SIGNIN_ROUTE)
            .andReturn();
        return String.format("Bearer %s", response.getBody().asString());
    }
}
