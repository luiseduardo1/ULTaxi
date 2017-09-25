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
    private static final String A_VALID_PASSWORD = "Beaubrun";
    private static final String AN_ID = "Ronald Beaubrun";
    private static final String API_USERS = "/api/users";

    @Before
    public void setUp() throws Exception {
        Thread t = new Thread(() -> {
            try {
                ULTaxiMain.main(new String[] {});
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        t.setDaemon(true);
        t.start();
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
            .baseUri("http://localhost")
            .accept(ContentType.JSON)
            .port(USER_TEST_SERVER_PORT)
            .contentType(ContentType.JSON);
    }

    private String givenUser() {
        UserDto userDto = new UserDto();
        userDto.setId(AN_ID);
        userDto.setName(A_VALID_NAME);
        userDto.setPassword(A_VALID_PASSWORD);
        Gson gson = new Gson();
        return gson.toJson(userDto);
    }

    private String givenUserWithInvalidName() {
        UserDto userDto = new UserDto();
        userDto.setId(AN_ID);
        userDto.setName("ronald.beaubrun@ulaval.ca");
        userDto.setPassword(A_VALID_PASSWORD);
        Gson gson = new Gson();
        return gson.toJson(userDto);
    }
}
