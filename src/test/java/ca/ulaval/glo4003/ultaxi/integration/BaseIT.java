package ca.ulaval.glo4003.ultaxi.integration;

import ca.ulaval.glo4003.ULTaxiMain;
import ca.ulaval.glo4003.ultaxi.integration.transportrequest.TransportRequestResourceIT;
import ca.ulaval.glo4003.ultaxi.integration.user.UserAuthenticationResourceIT;
import ca.ulaval.glo4003.ultaxi.integration.user.UserResourceIT;
import ca.ulaval.glo4003.ultaxi.integration.user.driver.DriverResourceIT;
import ca.ulaval.glo4003.ultaxi.integration.vehicle.VehicleResourceIT;
import io.restassured.RestAssured;
import org.junit.AfterClass;
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
public class BaseIT {

    @BeforeClass
    public static void setUpBaseServer() throws Exception {
        ULTaxiMain.start();

        RestAssured.baseURI = ULTaxiMain.getBaseURI();
        RestAssured.port = ULTaxiMain.getPort();
    }

    @AfterClass
    public static void tearDownBaseServer() throws Exception {
        ULTaxiMain.stop();
    }
}
