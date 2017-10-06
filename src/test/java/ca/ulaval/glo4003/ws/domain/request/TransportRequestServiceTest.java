package ca.ulaval.glo4003.ws.domain.request;

import ca.ulaval.glo4003.ws.api.transportrequest.dto.TransportRequestDto;
import ca.ulaval.glo4003.ws.domain.transportrequest.TransportRequest;
import ca.ulaval.glo4003.ws.domain.transportrequest.TransportRequestAssembler;
import ca.ulaval.glo4003.ws.domain.transportrequest.TransportRequestRepository;
import ca.ulaval.glo4003.ws.domain.transportrequest.TransportRequestService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class TransportRequestServiceTest {

    @Mock
    private TransportRequest transportRequest;
    @Mock
    private TransportRequestDto transportRequestDto;
    @Mock
    private TransportRequestRepository transportRequestRepository;
    @Mock
    private TransportRequestAssembler transportRequestAssembler;

    private TransportRequestService transportRequestService;

    @Before
    public void setUp() throws Exception {
        transportRequestService = new TransportRequestService(transportRequestRepository, transportRequestAssembler);
    }

    @Test
    public void givenAValidTransportRequest_whenSendRequest_thenRequestIsAdded() {
        willReturn(transportRequest).given(transportRequestAssembler).create(transportRequestDto);

        transportRequestService.sendRequest(transportRequestDto);

        verify(transportRequestRepository).save(transportRequest);
    }

}
