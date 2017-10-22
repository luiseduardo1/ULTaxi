package ca.ulaval.glo4003.ultaxi.integration;

import static io.restassured.RestAssured.given;

import ca.ulaval.glo4003.ultaxi.domain.user.Role;
import ca.ulaval.glo4003.ultaxi.transfer.user.UserDto;
import com.google.gson.Gson;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.lang3.RandomStringUtils;

import javax.ws.rs.core.Response.Status;
import java.util.HashMap;
import java.util.Map;

public abstract class IntegrationTest {

    protected static final String USERS_ROUTE = "/api/users";
    protected static final String USER_AUTHENTICATION_ROUTE = USERS_ROUTE + "/auth";
    protected static final String SIGNIN_ROUTE = USER_AUTHENTICATION_ROUTE + "/signin";
    protected static final String SIGNOUT_ROUTE = USER_AUTHENTICATION_ROUTE + "/signout";

    private static final String AUTHENTICATION_TOKEN_PREFIX = "Bearer ";
    private static final int RANDOM_WORD_LENGTH = 20;

    protected String authenticationToken = "";

    private static final String extractAuthenticationToken(Response response) {
        return AUTHENTICATION_TOKEN_PREFIX + response.getBody().asString();
    }

    private static final Response executePostRequest(RequestSpecification requestSpecification) {
        return requestSpecification
            .when()
            .post();
    }

    private static final Response executePostRequest(RequestSpecification requestSpecification,
        String body) {
        return requestSpecification
            .body(body)
            .when()
            .post();
    }

    private static final Response executeGetRequest(RequestSpecification requestSpecification,
        Map<String, ?> queryParameters) {
        return requestSpecification
            .queryParams(queryParameters)
            .when()
            .get();
    }

    private static final RequestSpecification createAuthenticatedRequestSpecification(String path,
        String authorizationToken) {
        return createBasicRequestSpecification(path)
            .header("Authorization", authorizationToken);
    }

    private static final RequestSpecification createBasicRequestSpecification(String path) {
        return given()
            .accept(ContentType.JSON)
            .contentType(ContentType.JSON)
            .header("Connection", "Close")
            .basePath(path);
    }

    private static final String createGenericRoleUserData(Role role) {
        String lowercaseRole = role.name().toLowerCase();
        return createUserData(
            lowercaseRole + "Username",
            lowercaseRole + "Password",
            lowercaseRole + "@ultaxi.ca"
        );
    }

    protected static final String createUserData(String username, String password, String email) {
        UserDto userDto = new UserDto();
        userDto.setUserName(username);
        userDto.setPassword(password);
        userDto.setEmail(email);

        Gson gson = new Gson();
        return gson.toJson(userDto);
    }

    protected Response authenticateAs(String userData) {
        signout();
        Response response = executePostRequest(
            createBasicRequestSpecification(SIGNIN_ROUTE), userData
        );
        authenticationToken = extractAuthenticationToken(response);
        return response;
    }

    protected Response authenticateAs(Role role) {
        return authenticateAs(
            createGenericRoleUserData(role)
        );
    }

    protected Response signout() {
        return executePostRequest(
            createAuthenticatedRequestSpecification(SIGNOUT_ROUTE, authenticationToken)
        );
    }

    protected Response authenticatedPost(String path, String body) {
        return executePostRequest(
            createAuthenticatedRequestSpecification(path, authenticationToken),
            body
        );
    }

    protected Response authenticatedGet(String path) {
        return authenticatedGet(path, new HashMap<>());
    }

    protected Response authenticatedGet(String path, Map<String, ?> queryParameters) {
        return executeGetRequest(
            createAuthenticatedRequestSpecification(path, authenticationToken),
            queryParameters
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
            createBasicRequestSpecification(path),
            queryParameters
        );
    }

    protected void assertStatusCode(Response response, Status status) {
        response
            .then()
            .assertThat()
            .statusCode(status.getStatusCode());
    }

    protected Map<String, String> createSearchQueryParameter(String parameter, String value) {
        Map<String, String> queryParameters = new HashMap<>();
        queryParameters.put(parameter, value);
        return queryParameters;
    }

    protected String generateRandomWord() {
        return RandomStringUtils.randomAlphabetic(RANDOM_WORD_LENGTH);
    }
}
