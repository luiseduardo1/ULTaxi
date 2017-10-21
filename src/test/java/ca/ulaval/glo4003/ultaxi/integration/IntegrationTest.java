package ca.ulaval.glo4003.ultaxi.integration;

import static io.restassured.RestAssured.given;

import ca.ulaval.glo4003.ultaxi.domain.user.Role;
import ca.ulaval.glo4003.ultaxi.transfer.user.UserDto;
import com.google.gson.Gson;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import javax.ws.rs.core.Response.Status;
import java.util.HashMap;
import java.util.Map;

public abstract class IntegrationTest {

    private static final String USERS_ROUTE = "/api/users";
    private static final String USER_AUTHENTICATION_ROUTE = USERS_ROUTE + "/auth";
    private static final String SIGNIN_ROUTE = USER_AUTHENTICATION_ROUTE + "/signin";
    private static final String AUTHENTICATION_TOKEN_PREFIX = "Bearer ";

    protected String authenticationToken = getGenericRoleUserAuthenticationToken(Role.Anonymous);

    protected void authenticateAsUser(String username, String password, String email) {
        authenticationToken = getAuthenticationToken(
            createUserData(username, password, email)
        );
    }

    protected void authenticateAsRole(Role role) {
        authenticationToken = getGenericRoleUserAuthenticationToken(role);
    }

    protected Response authenticatedPost(String path, String body, String authorizationToken) {
        return executePostRequest(
            createAuthenticatedRequestSpecification(path, authorizationToken), body
        );
    }

    protected Response authenticatedGet(String path, String authorizationToken) {
        return authenticatedGet(path, authorizationToken, new HashMap<>());
    }

    protected Response authenticatedGet(String path, String authorizationToken,
        Map<String, ?> queryParameters) {
        return executeGetRequest(
            createAuthenticatedRequestSpecification(path, authorizationToken),
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

    protected String getGenericRoleUserAuthenticationToken(Role role) {
        return getAuthenticationToken(
            createGenericRoleUserData(role)
        );
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

    private static String getAuthenticationToken(String userData) {
        Response response = executePostRequest(
            createBasicRequestSpecification(SIGNIN_ROUTE), userData
        );
        return AUTHENTICATION_TOKEN_PREFIX + response.getBody().asString();
    }

    private static final String createGenericRoleUserData(Role role) {
        String lowercaseRole = role.name().toLowerCase();
        return createUserData(
            lowercaseRole + "Username",
            lowercaseRole + "Password",
            lowercaseRole + "@ultaxi.ca"
        );
    }

    private static final String createUserData(String username, String password, String email) {
        UserDto userDto = new UserDto();
        userDto.setUserName(username);
        userDto.setPassword(password);
        userDto.setEmail(email);

        Gson gson = new Gson();
        return gson.toJson(userDto);
    }
}
