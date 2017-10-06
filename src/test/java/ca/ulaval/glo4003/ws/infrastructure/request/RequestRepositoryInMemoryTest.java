package ca.ulaval.glo4003.ws.infrastructure.request;

import ca.ulaval.glo4003.ws.domain.request.Request;
import ca.ulaval.glo4003.ws.domain.request.RequestRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;


@RunWith(MockitoJUnitRunner.class)
public class RequestRepositoryInMemoryTest {

    @Mock
    private Request request;

    private RequestRepository requestRepository;

    @Before
    public void setUp() {
        requestRepository = new RequestRepositoryInMemory();
    }

    @Test
    public void givenRequest_whenSave_ThenReturnRequestInMemory() {
        requestRepository.save(request);

        Request foundRequest = requestRepository.findById(request.getRequestId());

        assertEquals(request, foundRequest);
    }

}
