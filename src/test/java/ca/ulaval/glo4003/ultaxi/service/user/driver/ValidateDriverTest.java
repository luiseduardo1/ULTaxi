package ca.ulaval.glo4003.ultaxi.service.user.driver;

import ca.ulaval.glo4003.ultaxi.builder.DriverBuilder;
import ca.ulaval.glo4003.ultaxi.domain.user.User;
import ca.ulaval.glo4003.ultaxi.domain.user.UserRepository;
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

    private DriverBuilder driverData;

    @Before
    public void setUp() {
        validateDriver = new ValidateDriver(userRepository);
        driverData = new DriverBuilder();
    }

    @Test(expected = SinAlreadyExistException.class)
    public void givenADriverWithExistingSin_whenVerifySin_thenTrowSinAlreadyExistException() {
        DriverDto driverDto = new DriverDto();
        String a_VALID_SIN = "972487086";
        String A_USERNAME = "user1234";
        String A_PASSWORD = "password1234";
        driverDto.setSin(a_VALID_SIN);
        driverDto.setUsername(A_USERNAME);
        driverDto.setPassword(A_PASSWORD);
        willReturn(new DriverSearchQueryBuilderInMemory(givenDrivers())).given(userRepository).searchDrivers();

        validateDriver.verifySin(driverDto);
    }

    private Map<String, User> givenDrivers() {
        Map<String, User> drivers = new HashMap<>();
        drivers.put("1", driverData.createDriver("Ronald", "Macdonald", "972487086"));
        return drivers;
    }

}