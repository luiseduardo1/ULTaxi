package ca.ulaval.glo4003.ultaxi.infrastructure.transportrequest;

import ca.ulaval.glo4003.ultaxi.domain.transportrequest.TransportRequest;
import ca.ulaval.glo4003.ultaxi.domain.transportrequest.TransportRequestRepository;
import ca.ulaval.glo4003.ultaxi.domain.transportrequest.TransportRequestSearchQueryBuilder;
import ca.ulaval.glo4003.ultaxi.domain.transportrequest.exception.NonExistentTransportRequestException;

import java.util.HashMap;
import java.util.Map;

public class TransportRequestRepositoryInMemory implements TransportRequestRepository {

    private final Map<String, TransportRequest> transportRequests = new HashMap<>();

    @Override
    public TransportRequest findById(String id) {
        TransportRequest foundTransportRequest = transportRequests.get(id);
        if (foundTransportRequest == null) {
            throw new NonExistentTransportRequestException(
                String.format("Transport request doesn't exist.")
            );
        }
        return foundTransportRequest;
    }

    @Override
    public void save(TransportRequest transportRequest) {
        transportRequests.put(transportRequest.getId(), transportRequest);
    }

    @Override
    public void update(TransportRequest transportRequest) {
        String transportRequestId = transportRequest.getId();
        if (findById(transportRequestId) == null) {
            throw new NonExistentTransportRequestException(
                String.format("Transport request doesn't exist.")
            );
        }
        transportRequests.put(transportRequestId, transportRequest);
    }

    @Override
    public TransportRequestSearchQueryBuilder searchTransportRequests() {
        return new TransportRequestSearchQueryBuilderInMemory(transportRequests);
    }
}
