package ca.ulaval.glo4003.ws;

import ca.ulaval.glo4003.ULTaxiMain;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import static java.lang.Thread.sleep;

@RunWith(Suite.class)
@Suite.SuiteClasses( {UserAuthenticationResourceIT.class, UserResourceIT.class})
public class BaseIntegrationTest {
    private static int SLEEP_TIME = 500;

    @BeforeClass
    public static void setUpBaseServer() throws Exception {
        Thread thread = new Thread(() -> {
            try {
                ULTaxiMain.main(new String[] {});
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });
        thread.setDaemon(true);
        thread.start();
        sleep(SLEEP_TIME);
    }
}
