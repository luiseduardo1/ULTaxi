package ca.ulaval.glo4003.ws.domain.transportrequest;

public interface TransportRequestRepository {

    TransportRequest findById(String id);

    void save(TransportRequest transportRequest);
}
