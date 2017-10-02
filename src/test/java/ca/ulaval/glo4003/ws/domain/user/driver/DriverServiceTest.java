package ca.ulaval.glo4003.ws.domain.user.driver;

import ca.ulaval.glo4003.ws.api.user.dto.DriverDto;
import ca.ulaval.glo4003.ws.domain.user.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class DriverServiceTest {

    @Mock
    private Driver driver;
    @Mock
    private DriverDto driverDto;
    @Mock
    DriverAssembler driverAssembler;
    @Mock
    private UserRepository userRepository;

    private DriverService driverService;

    @Before
    public void setUp() {
        driverService = new DriverService(userRepository, driverAssembler);
    }

    @Test
    public void givenADriverWithValidName_whenAddDriver_thenDriverIsAdded() {
        willReturn(driver).given(driverAssembler).create(driverDto);

        driverService.addDriver(driverDto);

        verify(userRepository).save(driver);
    }
}