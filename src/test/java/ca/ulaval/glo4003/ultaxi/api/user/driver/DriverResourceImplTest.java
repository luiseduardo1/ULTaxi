package ca.ulaval.glo4003.ultaxi.api.user.driver;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.willThrow;

import ca.ulaval.glo4003.ultaxi.domain.user.exception.InvalidPhoneNumberException;
import ca.ulaval.glo4003.ultaxi.domain.user.exception.InvalidSinException;
import ca.ulaval.glo4003.ultaxi.domain.user.exception.InvalidUserNameException;
import ca.ulaval.glo4003.ultaxi.domain.user.exception.UserAlreadyExistsException;
import ca.ulaval.glo4003.ultaxi.service.user.driver.DriverService;
import ca.ulaval.glo4003.ultaxi.transfer.user.driver.DriverDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ws.rs.core.Response;

@RunWith(MockitoJUnitRunner.class)
public class DriverResourceImplTest {

    @Mock
    private DriverService driverService;
    @Mock
    private DriverDto driverDto;

    private DriverResource driverResource;

    @Before
    public void setUp() {
        driverResource = new DriverResourceImpl(driverService);
    }

    @Test
    public void givenValidDriver_whenCreateDriver_thenReturnsOk() {
        Response response = driverResource.createDriver(driverDto);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void givenAlreadyExistingDriver_whenCreateDriver_thenReturnsBadRequest() {
        willThrow(new UserAlreadyExistsException("User already exists."))
            .given(driverService)
            .addDriver(driverDto);

        Response response = driverResource.createDriver(driverDto);

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    @Test
    public void givenDriverWithInvalidName_whenCreateDriver_thenReturnsBadRequest() {
        willThrow(new InvalidUserNameException("User has an invalid userName."))
            .given(driverService)
            .addDriver(driverDto);

        Response response = driverResource.createDriver(driverDto);

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    @Test
    public void givenDriverWithInvalidPhoneNumber_whenCreateDriver_thenReturnsBadRequest() {
        willThrow(new InvalidPhoneNumberException("User has an invalid phone number."))
            .given(driverService)
            .addDriver(driverDto);

        Response response = driverResource.createDriver(driverDto);

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    @Test
    public void givenDriverWithInvalidSin_whenCreateDriver_thenReturnsBadRequest() {
        willThrow(new InvalidSinException("User has an invalid sin."))
            .given(driverService)
            .addDriver(driverDto);

        Response response = driverResource.createDriver(driverDto);

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

}