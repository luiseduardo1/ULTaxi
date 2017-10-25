package ca.ulaval.glo4003.ultaxi.domain.transportrequest;

public interface TransportRequestRepository {

    TransportRequest findById(String id);

    void save(TransportRequest transportRequest);

    TransportRequestSearchQueryBuilder searchTransportRequests();
}
