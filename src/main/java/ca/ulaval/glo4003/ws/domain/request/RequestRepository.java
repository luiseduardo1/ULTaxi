package ca.ulaval.glo4003.ws.domain.request;

import java.util.List;

public interface RequestRepository {

    List<Request> findAll();

    Request findById(String id);

    void save(Request request);
}
