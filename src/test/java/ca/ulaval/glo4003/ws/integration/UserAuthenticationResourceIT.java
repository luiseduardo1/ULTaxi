package ca.ulaval.glo4003.ws.integration;

import static io.restassured.RestAssured.given;

import ca.ulaval.glo4003.ws.api.user.dto.UserDto;
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
public class UserAuthenticationResourceIT {

    private static final String API_USERS = "/api/users";
    private static final String API_USER_AUTHENTICATION = String.format("%s/auth", API_USERS);
    private static final String SIGNIN_ROUTE = String.format("%s/signin", API_USER_AUTHENTICATION);
    private static final String SIGNOUT_ROUTE = String.format("%s/signout", API_USER_AUTHENTICATION);
    private static final String A_VALID_PASSWORD = "Beaubrun";
    private static final String A_VALID_EMAIL = "valid.email.test@gmail.com";
    private static final String A_DIFFERENT_PASSWORD = "Nadir";
    private static final String A_ROLE = "Client";

    private static String aValidName;

    @Before
    public void setUp() throws Exception {
        aValidName = RandomStringUtils.randomAlphabetic(25);
    }

    @Test
    public void givenUser_whenAuthenticate_thenUserIsAuthenticated() {
        givenBaseUserServer()
            .body(givenUser())
            .when()
            .post(API_USERS);

        givenBaseUserServer()
            .body(givenUser())
            .when()
            .post(SIGNIN_ROUTE)
            .then()
            .statusCode(Response.Status.OK.getStatusCode());
    }

    @Test
    public void givenInexistingUser_whenAuthenticate_thenReturnsForbidden() {
        givenBaseUserServer()
            .body(givenUser())
            .when()
            .post(SIGNIN_ROUTE)
            .then()
            .statusCode(Response.Status.FORBIDDEN.getStatusCode());
    }

    @Test
    public void givenInvalidCredentials_whenAuthenticate_thenReturnsForbidden() {
        givenBaseUserServer()
            .body(givenUser())
            .when()
            .post(API_USERS);

        givenBaseUserServer()
            .body(givenUserWithInvalidPassword())
            .post(SIGNIN_ROUTE)
            .then()
            .statusCode(Response.Status.FORBIDDEN.getStatusCode());
    }

    @Test
    public void givenAuthenticatedUser_whenSignOut_thenUserIsSignedOut() {
        givenBaseUserServer()
            .body(givenUser())
            .when()
            .post(API_USERS);
        io.restassured.response.Response response = givenBaseUserServer()
            .body(givenUser())
            .when()
            .post(SIGNIN_ROUTE)
            .andReturn();

        givenBaseUserServer()
            .header(
                "Authorization",
                response.getBody().asString()
            )
            .when()
            .post(SIGNOUT_ROUTE)
            .then()
            .statusCode(Response.Status.RESET_CONTENT.getStatusCode());
    }

    private RequestSpecification givenBaseUserServer() {
        return given()
            .accept(ContentType.JSON)
            .contentType(ContentType.JSON);
    }

    private String givenUser() {
        UserDto userDto = new UserDto();
        userDto.setName(aValidName);
        userDto.setPassword(A_VALID_PASSWORD);
        userDto.setEmail(A_VALID_EMAIL);
        userDto.setRole("Client");
        Gson gson = new Gson();
        return gson.toJson(userDto);
    }

    private String givenUserWithInvalidPassword() {
        UserDto userDto = new UserDto();
        userDto.setName(aValidName);
        userDto.setPassword(A_DIFFERENT_PASSWORD);
        userDto.setEmail(A_VALID_EMAIL);
        userDto.setRole(A_ROLE);
        Gson gson = new Gson();
        return gson.toJson(userDto);
    }
}
