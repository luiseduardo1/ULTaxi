package ca.ulaval.glo4003.ws;

import ca.ulaval.glo4003.ULTaxiMain;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;

@RunWith(MockitoJUnitRunner.class)
public class UserResourceIT {

    private static final int USER_TEST_SERVER_PORT = 8080;

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
        get("/users").then().body("name", Matchers.hasItem("Steve Jobs"));
    }

    private RequestSpecification givenBaseUserServer() {
        return given().accept(ContentType.JSON).port(USER_TEST_SERVER_PORT).contentType(ContentType.JSON);
    }
}
