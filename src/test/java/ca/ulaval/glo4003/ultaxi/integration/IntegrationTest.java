package ca.ulaval.glo4003.ultaxi.integration;

import static io.restassured.RestAssured.given;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import javax.ws.rs.core.Response.Status;
import java.util.HashMap;
import java.util.Map;

public abstract class IntegrationTest {

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
}
