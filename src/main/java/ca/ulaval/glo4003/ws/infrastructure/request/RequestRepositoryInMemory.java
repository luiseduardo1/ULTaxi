package ca.ulaval.glo4003.ws.infrastructure.request;

import ca.ulaval.glo4003.ws.domain.request.Request;
import ca.ulaval.glo4003.ws.domain.request.RequestRepository;
import jersey.repackaged.com.google.common.collect.Lists;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RequestRepositoryInMemory implements RequestRepository {

    private Map<String, Request> commands = new HashMap<>();

    @Override
    public void save(Request command) {
        commands.put(command.getId(), command);
    }

    public List<Request> findAll() {
        return Lists.newArrayList(commands.values());
    }

    public Request findById(String id){
        return commands.get(id);
    }
}
