package ca.ulaval.glo4003.ultaxi.integration;

import static java.lang.Thread.sleep;

import ca.ulaval.glo4003.ULTaxiMain;
import ca.ulaval.glo4003.ultaxi.integration.transportrequest.TransportRequestResourceIT;
import ca.ulaval.glo4003.ultaxi.integration.user.UserAuthenticationResourceIT;
import ca.ulaval.glo4003.ultaxi.integration.user.UserResourceIT;
import ca.ulaval.glo4003.ultaxi.integration.user.driver.DriverResourceIT;
import ca.ulaval.glo4003.ultaxi.integration.vehicle.VehicleResourceIT;
import io.restassured.RestAssured;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    UserAuthenticationResourceIT.class,
    UserResourceIT.class,
    TransportRequestResourceIT.class,
    VehicleResourceIT.class,
    DriverResourceIT.class
})
public class BaseIntegrationIT {

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
