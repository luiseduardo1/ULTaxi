package ca.ulaval.glo4003.ultaxi.infrastructure.transportrequest;

import ca.ulaval.glo4003.ultaxi.domain.geolocation.Geolocation;
import ca.ulaval.glo4003.ultaxi.domain.transportrequest.TransportRequest;
import jersey.repackaged.com.google.common.collect.Lists;

import java.util.List;

public class TransportRequestDevDataFactory {

    public List<TransportRequest> createMockData() {
        List<TransportRequest> transportRequests = Lists.newArrayList();

        TransportRequest transportRequest1 = new TransportRequest();
        transportRequest1.setId("1");
        transportRequest1.setVehicleType("Car");
        Geolocation geolocation = new Geolocation(12.4534,13.4534);
        transportRequest1.setStartingPosition(geolocation);
        transportRequests.add(transportRequest1);

        return transportRequests;
    }
}
