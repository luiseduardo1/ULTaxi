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
        return transportRequests.get(id);
    }

    @Override
    public void save(TransportRequest transportRequest) {
        transportRequests.put(transportRequest.getId(), transportRequest);
    }

    @Override
    public void put(TransportRequest transportRequest) {
        String transportRequestId = transportRequest.getId();
        if (findById(transportRequestId) == null) {
            throw new NonExistentTransportRequestException(
                    String.format("Transport Request don't exists.")
            );
        }
        transportRequests.put(transportRequest.getId(), transportRequest);
    }

    @Override
    public TransportRequestSearchQueryBuilder searchTransportRequests() {
        return new TransportRequestSearchQueryBuilderInMemory(transportRequests);
    }
}
