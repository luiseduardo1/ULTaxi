package ca.ulaval.glo4003.ultaxi.service.user.driver;

import ca.ulaval.glo4003.ultaxi.domain.user.Role;
import ca.ulaval.glo4003.ultaxi.domain.user.User;
import ca.ulaval.glo4003.ultaxi.domain.user.UserRepository;
import ca.ulaval.glo4003.ultaxi.domain.user.driver.Driver;
import ca.ulaval.glo4003.ultaxi.domain.user.exception.SinAlreadyExistException;
import ca.ulaval.glo4003.ultaxi.infrastructure.user.driver.DriverSearchQueryBuilderInMemory;
import ca.ulaval.glo4003.ultaxi.transfer.user.driver.DriverDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.BDDMockito.willReturn;

@RunWith(MockitoJUnitRunner.class)
public class ValidateDriverTest {

    @Mock
    private UserRepository userRepository;

    private ValidateDriver validateDriver;

    private String A_VALID_SIN = "972487086";
    private String A_USERNAME = "test1234";
    private String A_PASSWORD = "testPassword";

    @Before
    public void setUp() {
        validateDriver = new ValidateDriver(userRepository);
    }

    @Test(expected = SinAlreadyExistException.class)
    public void givenADriverWithExistingSin_whenVerifySin_thenTrowSinAlreadyExistException() {
        DriverDto driverDto = new DriverDto();
        driverDto.setSin(A_VALID_SIN);
        driverDto.setUserName(A_USERNAME);
        driverDto.setPassword(A_PASSWORD);
        willReturn(new DriverSearchQueryBuilderInMemory(givenDrivers())).given(userRepository).searchDrivers();

        validateDriver.VerifySin(driverDto);
    }

    private Map<String, User> givenDrivers() {
        Map<String, User> drivers = new HashMap<>();
        drivers.put("1", createDriver("Ronald", "Macdonald", "972487086"));
        return drivers;
    }

    private User createDriver(String firstName, String lastName, String sin) {
        Driver driver = new Driver();
        driver.setName(firstName);
        driver.setLastName(lastName);
        driver.setSin(sin);
        driver.setRole(Role.Driver);

        return driver;
    }
}