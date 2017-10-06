package ca.ulaval.glo4003.ws.domain.request;

public interface RequestRepository {

    Request findById(String id);

    void save(Request request);
}
