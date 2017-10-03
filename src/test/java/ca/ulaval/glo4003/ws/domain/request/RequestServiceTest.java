package ca.ulaval.glo4003.ws.domain.request;

import ca.ulaval.glo4003.ws.api.request.dto.RequestDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class RequestServiceTest {

    @Mock
    private Request request;
    @Mock
    private RequestDto requestDto;
    @Mock
    private RequestRepository requestRepository;
    @Mock
    private RequestAssembler requestAssembler;

    private RequestService requestService;

    @Before
    public void setUp() throws Exception {
        requestService = new RequestService(requestRepository, requestAssembler);
    }

    @Test
    public void givenAValidRequest_whenAddRequest_thenRequestIsAdded() {
        willReturn(request).given(requestAssembler).create(requestDto);

        requestService.sendTransportRequest(requestDto);

        verify(requestRepository).save(request);
    }
}
