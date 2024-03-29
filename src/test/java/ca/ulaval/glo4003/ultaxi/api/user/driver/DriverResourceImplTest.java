package ca.ulaval.glo4003.ultaxi.api.user.driver;

import ca.ulaval.glo4003.ultaxi.service.user.driver.DriverService;
import ca.ulaval.glo4003.ultaxi.transfer.user.driver.DriverDto;
import ca.ulaval.glo4003.ultaxi.transfer.user.driver.DriverSearchParameters;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ws.rs.core.Response;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Matchers.any;

@RunWith(MockitoJUnitRunner.class)
public class DriverResourceImplTest {

    private static final String A_SOCIAL_INSURANCE_NUMBER = "972487086";
    private static final String A_FIRST_NAME = "Ronald";
    private static final String A_LAST_NAME = "Macdonald";

    @Mock
    private DriverService driverService;
    @Mock
    private DriverDto driverDto;
    @Mock
    private List<DriverDto> driverDtos;

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
    public void givenValidSearchQueryWithAssociatedDriver_whenSearching_thenReturnsOk() {
        willReturn(driverDtos)
            .given(driverService)
            .searchBy(any(DriverSearchParameters.class));

        Response response = driverResource.searchBy(A_SOCIAL_INSURANCE_NUMBER, A_FIRST_NAME, A_LAST_NAME);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

}