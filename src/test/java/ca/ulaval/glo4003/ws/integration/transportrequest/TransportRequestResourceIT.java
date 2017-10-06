package ca.ulaval.glo4003.ws.integration.transportrequest;

import static io.restassured.RestAssured.given;

import ca.ulaval.glo4003.ws.transfer.transportrequest.TransportRequestDto;
import com.google.gson.Gson;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ws.rs.core.Response;

@RunWith(MockitoJUnitRunner.class)
public class TransportRequestResourceIT {

    private static final int TEST_SERVER_PORT = 8080;
    private static final String REQUEST_API = "/api/transportRequest";
    private static final String URL_BASE = "http://localhost";
    private static final String A_VALID_NOTE = "Note";
    private static final String A_VALID_VEHICLE_TYPE = "Car";
    private static final String AN_INVALID_VEHICLE_TYPE = "Invalid";
    private static final double A_VALID_LATITUDE = 45.12321;
    private static final double A_VALID_LONGITUDE = 15.34344;
    private static final double AN_INVALID_LATITUDE = -145.12321;
    private static final double AN_INVALID_LONGITUDE = 235.34344;

    @Test
    public void givenATransportRequest_whenSendRequest_thenRequestIsCreated() {
        givenBaseServer()
            .body(givenAValidTransportRequest())
            .when()
            .post(REQUEST_API)
            .then()
            .statusCode(Response.Status.CREATED.getStatusCode());
    }

    @Test
    public void givenATransportRequestWithInvalidLatitude_whenSendRequest_thenReturnsBadRequest() {
        givenBaseServer()
            .body(givenATransportRequestWithInvalidLatitude())
            .when()
            .post(REQUEST_API)
            .then()
            .statusCode(Response.Status.BAD_REQUEST.getStatusCode());
    }

    @Test
    public void givenATransportRequestWithInvalidLongitude_whenSendRequest_thenReturnsBadRequest() {
        givenBaseServer()
            .body(givenATransportRequestWithInvalidLongitude())
            .when()
            .post(REQUEST_API)
            .then()
            .statusCode(Response.Status.BAD_REQUEST.getStatusCode());
    }

    @Test
    public void givenATransportRequestWithInvalidVehicleType_whenSendRequest_thenReturnsBadRequest() {
        givenBaseServer()
            .body(givenATransportRequestWithInvalidVehicleType())
            .when()
            .post(REQUEST_API)
            .then()
            .statusCode(Response.Status.BAD_REQUEST.getStatusCode());
    }

    private RequestSpecification givenBaseServer() {
        return given()
            .baseUri(URL_BASE)
            .accept(ContentType.JSON)
            .port(TEST_SERVER_PORT)
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
}
