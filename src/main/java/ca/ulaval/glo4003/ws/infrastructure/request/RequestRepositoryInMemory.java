package ca.ulaval.glo4003.ws.infrastructure.request;

import ca.ulaval.glo4003.ws.domain.request.Request;
import ca.ulaval.glo4003.ws.domain.request.RequestRepository;

import java.util.HashMap;
import java.util.Map;

public class RequestRepositoryInMemory implements RequestRepository {

    private Map<String, Request> requests = new HashMap<>();

    @Override
    public Request findById(String requestId) {
        return requests.get(requestId);
    }

    @Override
    public void save(Request request) {
        requests.put(request.getRequestId(), request);
    }
}
