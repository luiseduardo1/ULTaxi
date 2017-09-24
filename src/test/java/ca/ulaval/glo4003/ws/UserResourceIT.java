package ca.ulaval.glo4003.ws;

import ca.ulaval.glo4003.ULTaxiMain;
import ca.ulaval.glo4003.ws.api.user.dto.UserDto;
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
    private static final String USER_CREATION_URL = "/users";

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
            .body(withUser())
            .when()
            .post(USER_CREATION_URL)
            .then()
            .statusCode(Response.Status.CREATED.getStatusCode());
    }

    private RequestSpecification givenBaseUserServer() {
        return given().accept(ContentType.JSON).port(USER_TEST_SERVER_PORT).contentType(ContentType.JSON);
    }

    private UserDto withUser() {
        UserDto userDto = new UserDto();
        userDto.setId(AN_ID);
        userDto.setName(A_VALID_NAME);
        userDto.setPassword(A_VALID_PASSWORD);
        return userDto;
    }
}
