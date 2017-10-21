package ca.ulaval.glo4003.ultaxi.api.user.driver;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Matchers.any;

import ca.ulaval.glo4003.ultaxi.domain.user.exception.EmptySearchResultsException;
import ca.ulaval.glo4003.ultaxi.domain.user.exception.InvalidPhoneNumberException;
import ca.ulaval.glo4003.ultaxi.domain.user.exception.InvalidSocialInsuranceNumberException;
import ca.ulaval.glo4003.ultaxi.domain.user.exception.InvalidUserNameException;
import ca.ulaval.glo4003.ultaxi.domain.user.exception.UserAlreadyExistsException;
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

@RunWith(MockitoJUnitRunner.class)
public class DriverResourceImplTest {

    @Mock
    private DriverService driverService;
    @Mock
    private DriverDto driverDto;
    @Mock
    private List<DriverDto> driverDtos;

    private DriverResource driverResource;

    private static final String A_SOCIAL_INSURANCE_NUMBER = "972487086";
    private static final String A_FIRST_NAME = "Ronald";
    private static final String A_LAST_NAME = "Macdonald";

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
    public void givenAlreadyExistDriver_whenCreateDriver_thenReturnsBadRequest() {
        willThrow(new UserAlreadyExistsException("User already exists."))
            .given(driverService)
            .addDriver(driverDto);

        Response response = driverResource.createDriver(driverDto);

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    @Test
    public void givenAlreadyExistSocialInsuranceNumber_whenCreateDriver_thenReturnsBadRequest(){
        willThrow(new SocialInsuranceNumberAlreadyExistException("Sin already exist"))
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
    public void givenDriverWithInvalidSocialInsuranceNumber_whenCreateDriver_thenReturnsBadRequest() {
        willThrow(new InvalidSocialInsuranceNumberException("User has an invalid social insurance number."))
            .given(driverService)
            .addDriver(driverDto);

        Response response = driverResource.createDriver(driverDto);

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    @Test
    public void givenValidSearchQueryWithNoAssociatedDriver_whenSearching_thenReturnsNotFound() {
        willThrow(new EmptySearchResultsException("No results found."))
            .given(driverService)
            .searchBy(any(DriverSearchParameters.class));

        Response response = driverResource.searchBy(A_SOCIAL_INSURANCE_NUMBER, A_FIRST_NAME, A_LAST_NAME);

        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
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