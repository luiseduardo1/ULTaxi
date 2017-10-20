package ca.ulaval.glo4003.ultaxi.integration.user;

import static io.restassured.RestAssured.given;

import ca.ulaval.glo4003.ultaxi.transfer.user.UserDto;
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
public class UserResourceIT {

    private static final String USERS_API = "/api/users";

    private static final String A_VALID_PASSWORD = "Macdonald";
    private static final String AN_INVALID_NAME = "ronald.macdonald@ulaval.ca";
    private static final String A_VALID_EMAIL = "valid.email.test@gmail.com";

    private String aValidName;

    @Before
    public void setUp() {
        aValidName = RandomStringUtils.randomAlphabetic(25);
    }

    @Test
    public void givenUserWithValidName_whenCreateUser_thenUserIsCreated() {
        givenBaseUserServer()
                .body(givenAValidUser())
                .when()
                .post(USERS_API)
                .then()
                .statusCode(Response.Status.OK.getStatusCode());
    }

    @Test
    public void givenAlreadyExistingUser_whenCreateUser_thenReturnsBadRequest() {
        givenBaseUserServer()
                .body(givenAValidUser())
                .when()
                .post(USERS_API);

        givenBaseUserServer()
                .body(givenAValidUser())
                .when()
                .post(USERS_API)
                .then()
                .statusCode(Response.Status.BAD_REQUEST.getStatusCode());
    }

    @Test
    public void givenUserWithInvalidName_whenCreateUser_thenReturnsBadRequest() {
        givenBaseUserServer()
                .body(givenAUserWithInvalidName())
                .when()
                .post(USERS_API)
                .then()
                .statusCode(Response.Status.BAD_REQUEST.getStatusCode());
    }

    private RequestSpecification givenBaseUserServer() {
        return given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON);
    }

    private String givenAValidUser() {
        return createUserJSON(aValidName, A_VALID_PASSWORD, A_VALID_EMAIL);
    }

    private String givenAUserWithInvalidName() {
        return createUserJSON(AN_INVALID_NAME, A_VALID_PASSWORD, A_VALID_EMAIL);
    }

    private String createUserJSON(String userName, String password, String email) {
        UserDto userDto = new UserDto();
        userDto.setUserName(userName);
        userDto.setPassword(password);
        userDto.setEmail(email);
        Gson gson = new Gson();
        return gson.toJson(userDto);
    }
}
