package ca.ulaval.glo4003.ws.infrastructure.transportrequest;

import ca.ulaval.glo4003.ws.domain.transportrequest.TransportRequest;
import ca.ulaval.glo4003.ws.domain.transportrequest.TransportRequestRepository;

import java.util.HashMap;
import java.util.Map;

public class TransportRequestRepositoryInMemory implements TransportRequestRepository {

    private Map<String, TransportRequest> transportRequests = new HashMap<>();

    @Override
    public TransportRequest findById(String id) {
        return transportRequests.get(id);
    }

    @Override
    public void save(TransportRequest transportRequest) {
        transportRequests.put(transportRequest.getId(), transportRequest);
    }
}
