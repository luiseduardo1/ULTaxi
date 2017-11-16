package ca.ulaval.glo4003.ultaxi.api.transportrequest;

import ca.ulaval.glo4003.ultaxi.domain.geolocation.exception.InvalidGeolocationException;
import ca.ulaval.glo4003.ultaxi.domain.transportrequest.exception.InvalidTransportRequestAssignationException;
import ca.ulaval.glo4003.ultaxi.domain.transportrequest.exception.NonExistentTransportRequestException;
import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4003.ultaxi.domain.user.User;
import ca.ulaval.glo4003.ultaxi.domain.user.driver.Driver;
import ca.ulaval.glo4003.ultaxi.domain.user.exception.EmptySearchResultsException;
import ca.ulaval.glo4003.ultaxi.domain.user.exception.NonExistentUserException;
import ca.ulaval.glo4003.ultaxi.domain.vehicle.VehicleType;
import ca.ulaval.glo4003.ultaxi.domain.vehicle.exception.InvalidVehicleTypeException;
import ca.ulaval.glo4003.ultaxi.service.transportrequest.TransportRequestService;
import ca.ulaval.glo4003.ultaxi.transfer.transportrequest.TransportRequestDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ws.rs.core.Response;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TransportRequestResourceImplTest {

    private static final String A_VALID_USERNAME = "username";
    private static final String A_VALID_TOKEN = "Valid token";
    private static final String A_VALID_DRIVER_USERNAME = "driverUsername";
    private static final String A_VALID_DRIVER_TOKEN = "Driver token";
    private static final String A_VALID_TRANSPORT_REQUEST_ID = "1";
    private static final VehicleType A_VEHICLE_TYPE = VehicleType.CAR;

    @Mock
    private TransportRequestService transportRequestService;
    @Mock
    private TransportRequestDto transportRequestDto;
    @Mock
    private List<TransportRequestDto> transportRequestDtos;
    @Mock
    private User user;
    @Mock
    private Driver driver;

    private TransportRequestResource transportRequestResource;

    @Before
    public void setUp() throws Exception {
        transportRequestResource = new TransportRequestResourceImpl(transportRequestService);
        when(user.getUsername()).thenReturn(A_VALID_USERNAME);
        when(driver.getVehicleType()).thenReturn(A_VEHICLE_TYPE);
        when(driver.getUsername()).thenReturn(A_VALID_DRIVER_USERNAME);
        when(transportRequestService.searchBy(A_VALID_DRIVER_TOKEN)).thenReturn
            (transportRequestDtos);
    }

    @Test
    public void givenAValidTransportRequest_whenSendRequest_thenReturnsCreated() {
        Response response = transportRequestResource.sendTransportRequest(A_VALID_TOKEN, transportRequestDto);

        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
    }

    /*@Test
    public void givenATransportRequestWithAnInvalidVehicleType_whenSendRequest_thenReturnsBadRequest() {
        willThrow(new InvalidVehicleTypeException("TransportRequest has an invalid vehicle type."))
            .given(transportRequestService)
            .sendRequest(transportRequestDto, A_VALID_TOKEN);

        Response response = transportRequestResource.sendTransportRequest(A_VALID_TOKEN, transportRequestDto);

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    } */

    /*@Test
    public void givenATransportRequestWithAnInvalidGeolocation_whenSendRequest_thenReturnsBadRequest() {
        willThrow(new InvalidGeolocationException("TransportRequest has an invalid geolocation."))
            .given(transportRequestService)
            .sendRequest(transportRequestDto, A_VALID_TOKEN);

        Response response = transportRequestResource.sendTransportRequest(A_VALID_TOKEN, transportRequestDto);

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }*/

    @Test
    public void givenAnAuthenticatedDriver_whenSearchAvailableTransportRequests_thenReturnsOk() {
        Response response = transportRequestResource.searchAvailableTransportRequests(A_VALID_DRIVER_TOKEN);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void
    givenATransportRequestResource_whenSearchAvailableTransportRequests_thenDelegateToRequestTransportService() {
        transportRequestResource.searchAvailableTransportRequests(A_VALID_DRIVER_TOKEN);

        verify(transportRequestService).searchBy(A_VALID_DRIVER_TOKEN);
    }

    /*@Test
    public void givenNoAvailableTransportRequests_whenSearchAvailableTransportRequests_thenReturnsNotFound() {
        willThrow(new EmptySearchResultsException("No transport requests found."))
            .given(transportRequestService)
            .searchBy(A_VALID_DRIVER_TOKEN);

        Response response = transportRequestResource.searchAvailableTransportRequests(A_VALID_DRIVER_TOKEN);

        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }*/

    @Test
    public void givenValidSearchQuery_whenSearchAvailableTransportRequests_thenReturnsOk() {
        willReturn(transportRequestDtos)
            .given(transportRequestService)
            .searchBy(A_VALID_DRIVER_TOKEN);

        Response response = transportRequestResource.searchAvailableTransportRequests(A_VALID_DRIVER_TOKEN);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void givenAnAuthenticatedDriver_whenAssignTransportRequest_thenReturnsOk(){
        Response response = transportRequestResource.assignTransportRequest(A_VALID_DRIVER_TOKEN, A_VALID_TRANSPORT_REQUEST_ID);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void givenATranportRequestId_whenAssignTransportRequest_thenDelegateToTransportService(){
        transportRequestResource.assignTransportRequest(A_VALID_DRIVER_TOKEN, A_VALID_TRANSPORT_REQUEST_ID);

        verify(transportRequestService).assignTransportRequest(A_VALID_DRIVER_TOKEN,A_VALID_TRANSPORT_REQUEST_ID);
    }

    @Test
    public void givenNonExistentTransportRequest_whenAssignTransportRequest_thenReturnsNotFound() {
        willThrow(new NonExistentTransportRequestException("Non existing transport request"))
                .given(transportRequestService)
                .assignTransportRequest(A_VALID_DRIVER_TOKEN, A_VALID_TRANSPORT_REQUEST_ID);

        Response response = transportRequestResource.assignTransportRequest(A_VALID_DRIVER_TOKEN, A_VALID_TRANSPORT_REQUEST_ID);

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    @Test
    public void givenNonExistentDriver_whenAssignTransportRequest_thenReturnsBadRequest() {
        willThrow(new NonExistentUserException("Non existing user"))
                .given(transportRequestService)
                .assignTransportRequest(A_VALID_DRIVER_TOKEN, A_VALID_TRANSPORT_REQUEST_ID);

        Response response = transportRequestResource.assignTransportRequest(A_VALID_DRIVER_TOKEN, A_VALID_TRANSPORT_REQUEST_ID);

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    @Test
    public void givenNonValidAssociation_whenAssociatingVehicle_thenReturnsBadRequest() {
        willThrow(new InvalidTransportRequestAssignationException("Invalid vehicle association"))
                .given(transportRequestService)
                .assignTransportRequest(A_VALID_DRIVER_TOKEN, A_VALID_TRANSPORT_REQUEST_ID);

        Response response = transportRequestResource.assignTransportRequest(A_VALID_DRIVER_TOKEN, A_VALID_TRANSPORT_REQUEST_ID);

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }
}
