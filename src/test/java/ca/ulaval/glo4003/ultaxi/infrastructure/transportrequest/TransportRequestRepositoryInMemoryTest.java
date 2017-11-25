package ca.ulaval.glo4003.ultaxi.infrastructure.transportrequest;

import static org.junit.Assert.assertEquals;

import ca.ulaval.glo4003.ultaxi.domain.transportrequest.TransportRequest;
import ca.ulaval.glo4003.ultaxi.domain.transportrequest.TransportRequestRepository;
import ca.ulaval.glo4003.ultaxi.domain.transportrequest.exception.NonExistentTransportRequestException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class TransportRequestRepositoryInMemoryTest {

    private static final String A_INVALID_TRANSPORT_ID = "4234";

    @Mock
    private TransportRequest transportRequest;

    private TransportRequestRepository transporRequestRepository;

    @Before
    public void setUp() {
        transporRequestRepository = new TransportRequestRepositoryInMemory();
    }

    @Test
    public void givenTransportRequest_whenSave_thenReturnTransporRequestInMemory() {
        transporRequestRepository.save(transportRequest);

        TransportRequest foundTransportRequest = transporRequestRepository.findById(transportRequest.getId());

        assertEquals(transportRequest, foundTransportRequest);
    }

    @Test(expected = NonExistentTransportRequestException.class)
    public void givenNonExistentTransportRequest_whenFindById_thenThrowsException() {
        transporRequestRepository.findById(A_INVALID_TRANSPORT_ID);
    }

}
