package ca.ulaval.glo4003.ultaxi.domain.transportrequest;

import java.util.List;

public interface TransportRequestSearchQueryBuilder {

    List<TransportRequest> findAll();

    TransportRequestSearchQueryBuilder withVehicleType(String vehicleType);
}
