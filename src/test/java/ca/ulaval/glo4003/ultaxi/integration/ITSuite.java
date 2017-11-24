package ca.ulaval.glo4003.ultaxi.integration;

import ca.ulaval.glo4003.ULTaxiMain;
import ca.ulaval.glo4003.ultaxi.integration.transportrequest.TransportRequestResourceIT;
import ca.ulaval.glo4003.ultaxi.integration.user.UserAuthenticationResourceIT;
import ca.ulaval.glo4003.ultaxi.integration.user.ClientResourceIT;
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
    ClientResourceIT.class,
    TransportRequestResourceIT.class,
    VehicleResourceIT.class,
    DriverResourceIT.class
})
public class ITSuite {

    private static final String[] ARGUMENTS = {"--server-port=8080"};

    @BeforeClass
    public static void setUpBaseServer() throws Exception {
        ULTaxiMain.main(ARGUMENTS);

        RestAssured.baseURI = ULTaxiMain.getBaseURI();
        RestAssured.port = ULTaxiMain.getPort();
    }

    @AfterClass
    public static void tearDownBaseServer() throws Exception {
        ULTaxiMain.stop();
    }
}
