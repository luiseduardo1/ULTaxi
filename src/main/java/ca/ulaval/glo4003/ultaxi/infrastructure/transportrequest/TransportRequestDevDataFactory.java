package ca.ulaval.glo4003.ultaxi.infrastructure.transportrequest;

import ca.ulaval.glo4003.ultaxi.domain.transportrequest.TransportRequest;
import jersey.repackaged.com.google.common.collect.Lists;

import java.util.List;

public class TransportRequestDevDataFactory {

    public List<TransportRequest> createMockData() {
        List<TransportRequest> transportRequests = Lists.newArrayList();

        TransportRequest transportRequest1 = new TransportRequest();
        transportRequest1.setId("1");
        transportRequests.add(transportRequest1);

        return transportRequests;
    }
}
