package ca.ulaval.glo4003.ws.api.request;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.willThrow;

import ca.ulaval.glo4003.ws.api.request.dto.RequestDto;
import ca.ulaval.glo4003.ws.domain.request.RequestService;
import ca.ulaval.glo4003.ws.domain.vehicle.InvalidVehicleTypeException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ws.rs.core.Response;

@RunWith(MockitoJUnitRunner.class)
public class RequestResourceITTest {

    @Mock
    private RequestService requestService;
    @Mock
    private RequestDto requestDto;

    private RequestResource requestResource;

    @Before
    public void setUp() throws Exception {
        requestResource = new RequestResourceImpl(requestService);
    }

    @Test
    public void givenAValidRequest_whenSendTransportRequest_thenReturnsOk() {
        Response response = requestResource.sendTransportRequest(requestDto);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void givenARequestWithAnInvalidVehicleType_whenSendRequest__thenReturnsBadRequest() {
        willThrow(new InvalidVehicleTypeException("Request has an invalid vehicle type."))
            .given(requestService)
            .sendTransportRequest(requestDto);

        Response response = requestResource.sendTransportRequest(requestDto);

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }
}
