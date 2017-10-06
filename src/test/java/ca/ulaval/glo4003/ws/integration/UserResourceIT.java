package ca.ulaval.glo4003.ws.integration;

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

import static io.restassured.RestAssured.given;

@RunWith(MockitoJUnitRunner.class)
public class UserResourceIT {

    private static final String A_VALID_PASSWORD = "Beaubrun";
    private static final String AN_INVALID_NAME = "ronald.beaubrun@ulaval.ca";
    private static final String API_USERS = "/api/users";

    private static String aValidName;

    @Before
    public void setUp() {
        aValidName = RandomStringUtils.randomAlphabetic(25);
    }


    @Test
    public void givenUserWithValidName_whenCreateUser_thenUserIsCreated() {
        givenBaseUserServer()
            .body(givenUser())
            .when()
            .post(API_USERS)
            .then()
            .statusCode(Response.Status.OK.getStatusCode());
    }

    @Test
    public void givenAlreadyExistingUser_whenCreateUser_thenReturnsBadRequest() {
        givenBaseUserServer()
            .body(givenUser())
            .when()
            .post(API_USERS);

        givenBaseUserServer()
            .body(givenUser())
            .when()
            .post(API_USERS)
            .then()
            .statusCode(Response.Status.BAD_REQUEST.getStatusCode());
    }

    @Test
    public void givenUserWithInvalidName_whenCreateUser_thenReturnsBadRequest() {
        givenBaseUserServer()
            .body(givenUserWithInvalidName())
            .when()
            .post(API_USERS)
            .then()
            .statusCode(Response.Status.BAD_REQUEST.getStatusCode());
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
        Gson gson = new Gson();
        return gson.toJson(userDto);
    }

    private String givenUserWithInvalidName() {
        UserDto userDto = new UserDto();
        userDto.setName(AN_INVALID_NAME);
        userDto.setPassword(A_VALID_PASSWORD);
        Gson gson = new Gson();
        return gson.toJson(userDto);
    }
}
