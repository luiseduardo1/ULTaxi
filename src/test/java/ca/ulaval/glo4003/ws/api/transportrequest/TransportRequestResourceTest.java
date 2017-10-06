package ca.ulaval.glo4003.ws.api.transportrequest;

import ca.ulaval.glo4003.ws.api.transportrequest.dto.TransportRequestDto;
import ca.ulaval.glo4003.ws.domain.transportrequest.TransportRequestService;
import ca.ulaval.glo4003.ws.domain.vehicle.InvalidVehicleTypeException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.willThrow;

@RunWith(MockitoJUnitRunner.class)
public class TransportRequestResourceTest {

    @Mock
    private TransportRequestService transportRequestService;
    @Mock
    private TransportRequestDto transportRequestDto;

    private TransportRequestResource transportRequestResource;

    @Before
    public void setUp() throws Exception {
        transportRequestResource = new TransportRequestResourceImpl(transportRequestService);
    }

    @Test
    public void givenAValidTransportRequest_whenSendRequest_thenReturnsOk() {
        Response response = transportRequestResource.sendTransportRequest(transportRequestDto);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void givenATransportRequestWithAnInvalidVehicleType_whenSendRequest__thenReturnsBadRequest() {
        willThrow(new InvalidVehicleTypeException("TransportRequest has an invalid vehicle type."))
            .given(transportRequestService)
            .sendRequest(transportRequestDto);

        Response response = transportRequestResource.sendTransportRequest(transportRequestDto);

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }
}
