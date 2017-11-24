package ca.ulaval.glo4003.ultaxi.infrastructure.transportrequest;

import ca.ulaval.glo4003.ultaxi.domain.geolocation.Geolocation;
import ca.ulaval.glo4003.ultaxi.domain.transportrequest.TransportRequest;
import jersey.repackaged.com.google.common.collect.Lists;

import java.util.List;

public class TransportRequestDevDataFactory {

    public List<TransportRequest> createMockData() {

        TransportRequest transportRequest = new TransportRequest();
        transportRequest.setId("1");
        transportRequest.setVehicleType("Car");
        Geolocation geolocation = new Geolocation(12.4534,13.4534);
        transportRequest.setStartingPosition(geolocation);

        List<TransportRequest> transportRequests = Lists.newArrayList();
        transportRequests.add(transportRequest);

        return transportRequests;
    }
}
