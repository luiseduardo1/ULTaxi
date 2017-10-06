package ca.ulaval.glo4003.ws.integration;

import ca.ulaval.glo4003.ws.api.request.dto.RequestDto;
import com.google.gson.Gson;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ws.rs.core.Response;

import static io.restassured.RestAssured.given;

@RunWith(MockitoJUnitRunner.class)
public class RequestResourceIT {

    private static final int TEST_SERVER_PORT = 8080;
    private static final String REQUEST_API = "/api/request";
    private static final String URL_BASE = "http://localhost";
    private static final String A_VALID_NOTE = "Note";
    private static final String A_VALID_VEHICLE_TYPE = "Car";
    private static final String AN_INVALID_VEHICLE_TYPE = "Invalid";
    private static final double A_VALID_LATITUDE = 45.12321;
    private static final double A_VALID_LONGITUDE = 15.34344;
    private static final double AN_INVALID_LATITUDE = -145.12321;
    private static final double AN_INVALID_LONGITUDE = 235.34344;

    @Test
    public void givenARequest_whenSendTransportRequest_thenRequestIsCreated() {
        givenBaseServer()
            .body(givenAValidRequest())
            .when()
            .post(REQUEST_API)
            .then()
            .statusCode(Response.Status.CREATED.getStatusCode());
    }

    @Test
    public void givenRequestWithInvalidLatitude_whenSendTransportRequest_thenReturnsBadRequest() {
        givenBaseServer()
            .body(givenARequestWithInvalidLatitude())
            .when()
            .post(REQUEST_API)
            .then()
            .statusCode(Response.Status.BAD_REQUEST.getStatusCode());
    }

    @Test
    public void givenRequestWithInvalidLongitude_whenSendTransportRequest_thenReturnsBadRequest() {
        givenBaseServer()
            .body(givenARequestWithInvalidLongitude())
            .when()
            .post(REQUEST_API)
            .then()
            .statusCode(Response.Status.BAD_REQUEST.getStatusCode());
    }

    @Test
    public void givenRequestWithInvalidVehicleType_whenSendTransportRequest_thenReturnsBadRequest() {
        givenBaseServer()
            .body(givenARequestWithInvalidVehicleType())
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

    private String givenAValidRequest() {
        return createRequestJSON(A_VALID_VEHICLE_TYPE, A_VALID_NOTE, A_VALID_LATITUDE, A_VALID_LONGITUDE);
    }

    private String givenARequestWithInvalidLatitude() {
        return createRequestJSON(A_VALID_VEHICLE_TYPE, A_VALID_NOTE, AN_INVALID_LATITUDE,
            A_VALID_LONGITUDE);
    }

    private String givenARequestWithInvalidLongitude() {
        return createRequestJSON(A_VALID_VEHICLE_TYPE, A_VALID_NOTE, A_VALID_LATITUDE,
            AN_INVALID_LONGITUDE);
    }

    private String givenARequestWithInvalidVehicleType() {
        return createRequestJSON(AN_INVALID_VEHICLE_TYPE, A_VALID_NOTE, A_VALID_LATITUDE,
            AN_INVALID_LONGITUDE);
    }

    private String createRequestJSON(String vehicleType, String note, double latitude, double longitude) {
        RequestDto requestDto = new RequestDto();
        requestDto.setVehicleType(vehicleType);
        requestDto.setNote(note);
        requestDto.setLatitude(latitude);
        requestDto.setLongitude(longitude);
        Gson gson = new Gson();
        return gson.toJson(requestDto);
    }
}
