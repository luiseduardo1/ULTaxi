package ca.ulaval.glo4003.ws;

import ca.ulaval.glo4003.ULTaxiMain;
import ca.ulaval.glo4003.ws.api.user.dto.UserDto;
import com.google.gson.Gson;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ws.rs.core.Response;

import static io.restassured.RestAssured.given;

@RunWith(MockitoJUnitRunner.class)
public class UserResourceIT {

    private static final int USER_TEST_SERVER_PORT = 8080;
    private static final String A_VALID_NAME = "Ronald";
    private static final String AN_INVALID_NAME = "ronald.beaubrun@ulaval.ca";
    private static final String A_VALID_PASSWORD = "Beaubrun";
    private static final String USERS_API = "/api/users";
    private static final String URL_BASE = "http://localhost";

    @Before
    public void setUp() {
        Thread thread = new Thread(() -> {
            try {
                ULTaxiMain.main(new String[] {});
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        thread.setDaemon(true);
        thread.start();
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
            .baseUri(URL_BASE)
            .accept(ContentType.JSON)
            .port(USER_TEST_SERVER_PORT)
            .contentType(ContentType.JSON);
    }

    private String givenAValidUser() {
        return createUserJSON(A_VALID_NAME, A_VALID_PASSWORD);
    }

    private String givenAUserWithInvalidName() {
        return createUserJSON(AN_INVALID_NAME, A_VALID_PASSWORD);
    }

    private String createUserJSON(String name, String password) {
        UserDto userDto = new UserDto();
        userDto.setName(name);
        userDto.setPassword(password);

        Gson gson = new Gson();
        return gson.toJson(userDto);
    }
}
