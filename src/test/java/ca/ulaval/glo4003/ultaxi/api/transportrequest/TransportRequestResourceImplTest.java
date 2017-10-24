package ca.ulaval.glo4003.ultaxi.api.transportrequest;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4003.ultaxi.domain.geolocation.exception.InvalidGeolocationException;
import ca.ulaval.glo4003.ultaxi.domain.user.driver.Driver;
import ca.ulaval.glo4003.ultaxi.domain.vehicle.VehicleType;
import ca.ulaval.glo4003.ultaxi.domain.vehicle.exception.InvalidVehicleTypeException;
import ca.ulaval.glo4003.ultaxi.service.transportrequest.TransportRequestService;
import ca.ulaval.glo4003.ultaxi.service.user.UserAuthenticationService;
import ca.ulaval.glo4003.ultaxi.transfer.transportrequest.TransportRequestDto;
import ca.ulaval.glo4003.ultaxi.transfer.transportrequest.TransportRequestSearchParameters;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ws.rs.core.Response;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class TransportRequestResourceImplTest {

    @Mock
    private UserAuthenticationService userAuthenticationService;
    @Mock
    private TransportRequestService transportRequestService;
    @Mock
    private TransportRequestDto transportRequestDto;
    @Mock
    private List<TransportRequestDto> transportRequestDtos;
    @Mock
    private Driver driver;

    private static final String A_VALID_TOKEN = "Valid token";
    private static final VehicleType A_VEHICLE_TYPE = VehicleType.Car;
    private TransportRequestResource transportRequestResource;

    @Before
    public void setUp() throws Exception {
        transportRequestResource = new TransportRequestResourceImpl(transportRequestService, userAuthenticationService);
        when(userAuthenticationService.authenticateFromToken(A_VALID_TOKEN)).thenReturn(driver);
        when(driver.getVehicleType()).thenReturn(A_VEHICLE_TYPE);
        when(transportRequestService.searchBy(any(TransportRequestSearchParameters.class))).thenReturn(transportRequestDtos);
    }

    @Test
    public void givenAValidTransportRequest_whenSendRequest_thenReturnsCreated() {
        Response response = transportRequestResource.sendTransportRequest(transportRequestDto);

        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
    }

    @Test
    public void givenATransportRequestWithAnInvalidVehicleType_whenSendRequest__thenReturnsBadRequest() {
        willThrow(new InvalidVehicleTypeException("TransportRequest has an invalid vehicle type."))
                .given(transportRequestService)
                .sendRequest(transportRequestDto);

        Response response = transportRequestResource.sendTransportRequest(transportRequestDto);

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    @Test
    public void givenATransportRequestWithAnInvalidGeolocation_whenSendRequest_thenReturnsBadRequest() {
        willThrow(new InvalidGeolocationException("TransportRequest has an invalid starting position geolocation."))
            .given(transportRequestService)
            .sendRequest(transportRequestDto);

        Response response = transportRequestResource.sendTransportRequest(transportRequestDto);

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    @Test
    public void givenAnAuthenticatedDriver_whenSearchAvailableTransportRequest_thenReturnsOk() {
        Response response = transportRequestResource.searchAvailableTransportRequest(A_VALID_TOKEN);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void givenATransportRequestResource_whenSearchAvailableTransportRequest_thenDelegateToRequestTransportService() {
        transportRequestResource.searchAvailableTransportRequest(A_VALID_TOKEN);

        verify(transportRequestService).searchBy(any(TransportRequestSearchParameters.class));
    }
}
