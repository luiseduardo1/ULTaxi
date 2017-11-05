package ca.ulaval.glo4003.ultaxi.infrastructure.transportrequest;

import ca.ulaval.glo4003.ultaxi.domain.transportrequest.TransportRequest;
import ca.ulaval.glo4003.ultaxi.domain.transportrequest.TransportRequestRepository;
import ca.ulaval.glo4003.ultaxi.domain.transportrequest.exception.NonExistentTransportRequestException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;


@RunWith(MockitoJUnitRunner.class)
public class TransportRequestRepositoryInMemoryTest {

    @Mock
    private TransportRequest transportRequest;

    private TransportRequestRepository transporRequestRepository;

    @Before
    public void setUp() {
        transporRequestRepository = new TransportRequestRepositoryInMemory();
    }

    @Test
    public void givenTransportRequest_whenSave_ThenReturnTransporRequestInMemory() {
        transporRequestRepository.save(transportRequest);

        TransportRequest foundTransportRequest = transporRequestRepository.findById(transportRequest.getId());

        assertEquals(transportRequest, foundTransportRequest);
    }

    @Test(expected = NonExistentTransportRequestException.class)
    public void givenNonExistentTransportRequest_whenPut_thenThrowsException() {
        transporRequestRepository.put(transportRequest);
    }

}
