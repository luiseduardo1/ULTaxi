package ca.ulaval.glo4003.ws.infrastructure.transportrequest;

import static org.junit.Assert.assertEquals;

import ca.ulaval.glo4003.ws.domain.transportrequest.TransportRequest;
import ca.ulaval.glo4003.ws.domain.transportrequest.TransportRequestRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class TransportRequestRepositoryInMemoryTest {

    @Mock
    private TransportRequest transportRequest;

    private TransportRequestRepository transportTransportRequestRepository;

    @Before
    public void setUp() {
        transportTransportRequestRepository = new TransportRequestRepositoryInMemory();
    }

    @Test
    public void givenTransportRequest_whenSave_ThenReturnRequestInMemory() {
        transportTransportRequestRepository.save(transportRequest);

        TransportRequest foundTransportRequest = transportTransportRequestRepository.findById(transportRequest.getId());

        assertEquals(transportRequest, foundTransportRequest);
    }

}
