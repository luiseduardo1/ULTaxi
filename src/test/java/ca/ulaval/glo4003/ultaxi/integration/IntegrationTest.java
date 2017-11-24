package ca.ulaval.glo4003.ultaxi.integration;

import ca.ulaval.glo4003.ultaxi.domain.user.Role;
import ca.ulaval.glo4003.ultaxi.transfer.transportrequest.TransportRequestDto;
import ca.ulaval.glo4003.ultaxi.transfer.user.AuthenticationDto;
import ca.ulaval.glo4003.ultaxi.transfer.user.client.ClientDto;
import ca.ulaval.glo4003.ultaxi.transfer.user.driver.DriverDto;
import ca.ulaval.glo4003.ultaxi.transfer.vehicle.VehicleDto;
import com.google.gson.Gson;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.lang3.RandomStringUtils;

import javax.ws.rs.core.Response.Status;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public abstract class IntegrationTest {

    protected static final String API_ROUTE = "/api";
    protected static final String USERS_ROUTE = API_ROUTE + "/users";
    protected static final String DRIVERS_ROUTE = API_ROUTE + "/drivers";
    protected static final String VEHICLES_ROUTE = API_ROUTE + "/vehicles";
    protected static final String TRANSPORT_REQUEST_ROUTE = API_ROUTE + "/transport-requests";
    protected static final String ASSIGN_TRANSPORT_REQUEST_ROUTE = API_ROUTE + "/transport-requests/assign";
    protected static final String SEARCH_TRANSPORT_REQUEST_ROUTE = API_ROUTE + "/transport-requests/search";
    protected static final String USER_AUTHENTICATION_ROUTE = USERS_ROUTE + "/auth";
    protected static final String SIGNIN_ROUTE = USER_AUTHENTICATION_ROUTE + "/signin";
    protected static final String SIGNOUT_ROUTE = USER_AUTHENTICATION_ROUTE + "/signout";

    private static final String AUTHENTICATION_TOKEN_PREFIX = "Bearer ";
    private static final int RANDOM_WORD_LENGTH = 20;

    private String authenticationToken = "";

    protected Response authenticateAs(Role role, String index) {
        return authenticateAs(
            createSerializedGenericRoleCredentials(role, index)
        );
    }

    protected Response authenticateAs(String serializedUser) {
        signout();
        Response response = executePostRequest(
            createBasicRequestSpecification(SIGNIN_ROUTE), serializedUser
        );
        authenticationToken = extractAuthenticationToken(response);
        return response;
    }

    protected Response signout() {
        return executePostRequest(
            createAuthenticatedRequestSpecification(SIGNOUT_ROUTE, authenticationToken)
        );
    }

    protected Response authenticatedPost(String path, String body) {
        return executePostRequest(
            createAuthenticatedRequestSpecification(path, authenticationToken), body
        );
    }

    protected Response authenticatedGet(String path) {
        return authenticatedGet(path, new HashMap<>());
    }

    protected Response authenticatedGet(String path, Map<String, ?> queryParameters) {
        return executeGetRequest(
            createAuthenticatedRequestSpecification(path, authenticationToken), queryParameters
        );
    }

    protected Response authenticatedPut(String path, String body) {
        return executePutRequest(createAuthenticatedRequestSpecification(path, authenticationToken), body);
    }

    protected Response unauthenticatedPut(String path, String body) {
        return executePutRequest(
            createBasicRequestSpecification(path), body
        );
    }

    protected Response unauthenticatedPost(String path, String body) {
        return executePostRequest(
            createBasicRequestSpecification(path), body
        );
    }

    protected Response unauthenticatedGet(String path) {
        return unauthenticatedGet(path, new HashMap<>());
    }


    protected Response unauthenticatedGet(String path, Map<String, ?> queryParameters) {
        return executeGetRequest(
            createBasicRequestSpecification(path), queryParameters
        );
    }

    protected void assertStatusCode(Response response, Status status) {
        response
            .then()
            .assertThat()
            .statusCode(status.getStatusCode());
    }

    protected String createSerializedGenericRoleCredentials(Role role, String index) {
        String lowercaseRole = role.name().toLowerCase();
        return createSerializedCredentials(
                lowercaseRole + index + "Username",
                lowercaseRole + index + "Password"
        );
    }

    protected String createSerializedCredentials(String username, String password) {
        AuthenticationDto authenticationDto = new AuthenticationDto();
        authenticationDto.setUsername(username);
        authenticationDto.setPassword(password);

        return serializeDto(authenticationDto);
    }

    protected String createSerializedUser(String username, String password, String phoneNumber, String email) {
        ClientDto clientDto = new ClientDto();
        clientDto.setUsername(username);
        clientDto.setPassword(password);
        clientDto.setPhoneNumber(phoneNumber);
        clientDto.setEmailAddress(email);

        return serializeDto(clientDto);
    }

    protected String createSerializedDriver(String username, String password, String phoneNumber, String emailAddress,
        String firstName, String lastName, String socialInsuranceNumber) {
        DriverDto driverDto = new DriverDto();
        driverDto.setUsername(username);
        driverDto.setPassword(password);
        driverDto.setPhoneNumber(phoneNumber);
        driverDto.setEmailAddress(emailAddress);
        driverDto.setFirstName(firstName);
        driverDto.setLastName(lastName);
        driverDto.setSocialInsuranceNumber(socialInsuranceNumber);

        return serializeDto(driverDto);
    }

    protected String createSerializedVehicle(String type, String color, String model, String registrationNumber) {
        VehicleDto vehicleDto = new VehicleDto();
        vehicleDto.setType(type);
        vehicleDto.setColor(color);
        vehicleDto.setModel(model);
        vehicleDto.setRegistrationNumber(registrationNumber);

        return serializeDto(vehicleDto);
    }

    protected String createSerializedTransportRequest(String vehicleType, String note, double latitude,
        double longitude) {
        TransportRequestDto transportRequestDto = new TransportRequestDto();
        transportRequestDto.setVehicleType(vehicleType);
        transportRequestDto.setNote(note);
        transportRequestDto.setStartingPositionLatitude(latitude);
        transportRequestDto.setStartingPositionLongitude(longitude);

        return serializeDto(transportRequestDto);
    }

    protected String serializeDto(Object dto) {
        Gson gson = new Gson();
        return gson.toJson(dto);
    }

    protected Map<String, String> createSearchQueryParameter(String parameter, String value) {
        Map<String, String> queryParameters = new HashMap<>();
        queryParameters.put(parameter, value);
        return queryParameters;
    }

    protected String generateRandomWord() {
        return RandomStringUtils.randomAlphabetic(RANDOM_WORD_LENGTH);
    }

    private String extractAuthenticationToken(Response response) {
        return AUTHENTICATION_TOKEN_PREFIX + response.getBody().asString();
    }

    private Response executePostRequest(RequestSpecification requestSpecification) {
        return requestSpecification
            .when()
            .post();
    }

    private Response executePostRequest(RequestSpecification requestSpecification, String body) {
        return requestSpecification
            .body(body)
            .when()
            .post();
    }

    private Response executePutRequest(RequestSpecification requestSpecification, String body) {
        return requestSpecification
            .body(body)
            .when()
            .put();
    }

    private Response executeGetRequest(RequestSpecification requestSpecification, Map<String, ?> queryParameters) {
        return requestSpecification
            .queryParams(queryParameters)
            .when()
            .get();
    }

    private RequestSpecification createAuthenticatedRequestSpecification(String path, String authorizationToken) {
        return createBasicRequestSpecification(path)
            .header("Authorization", authorizationToken);
    }

    private RequestSpecification createBasicRequestSpecification(String path) {
        return given()
            .accept(ContentType.JSON)
            .contentType(ContentType.JSON)
            .header("Connection", "Close")
            .basePath(path);
    }
}
