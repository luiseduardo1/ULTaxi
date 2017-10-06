package ca.ulaval.glo4003.ws.integration;

import static java.lang.Thread.sleep;

import ca.ulaval.glo4003.ULTaxiMain;
import io.restassured.RestAssured;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    UserAuthenticationResourceIT.class,
    UserResourceIT.class,
    RequestResourceIT.class,
    VehicleResourceIT.class
})
public class BaseIntegrationTest {

    private static final int SLEEP_TIME = 500;
    private static final int SERVER_PORT = 8080;
    private static final String SERVER_BASE_URI = "http://localhost";

    @BeforeClass
    public static void setUpBaseServer() throws Exception {
        RestAssured.baseURI = SERVER_BASE_URI;
        RestAssured.port = SERVER_PORT;

        Thread thread = new Thread(() -> {
            try {
                ULTaxiMain.main(new String[]{});
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });
        thread.setDaemon(true);
        thread.start();
        sleep(SLEEP_TIME);
    }
}
