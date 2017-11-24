package ca.ulaval.glo4003.ultaxi.infrastructure.transportrequest;

import ca.ulaval.glo4003.ultaxi.domain.geolocation.Geolocation;
import ca.ulaval.glo4003.ultaxi.domain.transportrequest.TransportRequest;
import ca.ulaval.glo4003.ultaxi.domain.transportrequest.TransportRequestSearchQueryBuilder;
import ca.ulaval.glo4003.ultaxi.domain.search.exception.EmptySearchResultsException;
import ca.ulaval.glo4003.ultaxi.domain.vehicle.VehicleType;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class TransportRequestSearchQueryBuilderInMemoryTest {

    private static final double GEOLOCATION_PRECISION_LOSS_DELTA = 0.0001;
    private static final String VEHICLE_TYPE_NOT_PRESENT = "Limousine";
    private static final String VEHICLE_TYPE_PRESENT = "Van";

    private TransportRequestSearchQueryBuilder transportRequestSearchQueryBuilder;

    @Before
    public void setUp() {
        transportRequestSearchQueryBuilder = new TransportRequestSearchQueryBuilderInMemory(givenTransportRequests());
    }

    @Test(expected = EmptySearchResultsException.class)
    public void givenFilterWithNoCorrespondingTransportRequests_whenFindingAll_thenThrowsEmptySearchResultsException() {
        TransportRequestSearchQueryBuilder searchTransportRequest = transportRequestSearchQueryBuilder
            .withVehicleType(VEHICLE_TYPE_NOT_PRESENT);

        searchTransportRequest.findAll();
    }

    @Test
    public void givenAvailableTransportRequestAndVehicleTypeFilter_whenFindingAll_thenReturnsTheRightTransportRequest
        () {
        TransportRequestSearchQueryBuilder searchTransportRequest = transportRequestSearchQueryBuilder
            .withVehicleType(VEHICLE_TYPE_PRESENT);

        List<TransportRequest> foundTransportRequests = searchTransportRequest.findAll();
        TransportRequest foundTransportRequest = foundTransportRequests.get(0);

        TransportRequest expectedTransportRequest = aSecondTransportRequest();
        assertEquals(1, foundTransportRequests.size());
        assertEquals(expectedTransportRequest.getStartingPosition().getLatitude(), foundTransportRequest
            .getStartingPosition().getLatitude(), GEOLOCATION_PRECISION_LOSS_DELTA);
        assertEquals(expectedTransportRequest.getStartingPosition().getLatitude(), foundTransportRequest
            .getStartingPosition().getLatitude(), GEOLOCATION_PRECISION_LOSS_DELTA);
        assertEquals(expectedTransportRequest.getNote(), foundTransportRequest.getNote());
        assertEquals(expectedTransportRequest.getVehicleType(), foundTransportRequest.getVehicleType());
    }

    private Map<String, TransportRequest> givenTransportRequests() {
        Map<String, TransportRequest> transportRequests = new HashMap<>();
        transportRequests.put("1", aFirstTransportRequest());
        transportRequests.put("2", aSecondTransportRequest());
        transportRequests.put("3", aThirdTransportRequest());
        return transportRequests;
    }

    private TransportRequest aFirstTransportRequest() {
        return new TransportRequest("ClientA", new Geolocation(25.0, 26.0), "Note", VehicleType.CAR);
    }

    private TransportRequest aSecondTransportRequest() {
        return new TransportRequest("ClientB", new Geolocation(35.0, 36.0), "Note", VehicleType.VAN);
    }

    private TransportRequest aThirdTransportRequest() {
        return new TransportRequest("ClientC", new Geolocation(45.0, 46.0), "Note", VehicleType.CAR);
    }
}
