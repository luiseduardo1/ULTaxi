package ca.ulaval.glo4003.ultaxi.infrastructure.transportrequest;

import ca.ulaval.glo4003.ultaxi.domain.geolocation.Geolocation;
import ca.ulaval.glo4003.ultaxi.domain.transportrequest.TransportRequest;
import ca.ulaval.glo4003.ultaxi.domain.vehicle.VehicleType;
import jersey.repackaged.com.google.common.collect.Lists;

import java.util.List;

public class TransportRequestDevDataFactory {

    public List<TransportRequest> createMockData() {
        List<TransportRequest> transportRequests = Lists.newArrayList();

        Geolocation startingPosition = new Geolocation(38.1223, 43.2332);
        TransportRequest transportRequest = new TransportRequest("Johny", startingPosition, "A dev transport request", VehicleType.LIMOUSINE);
        transportRequests.add(transportRequest);

        return transportRequests;
    }
}
