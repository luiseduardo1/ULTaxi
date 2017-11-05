package ca.ulaval.glo4003.ultaxi.service.transportrequest;

import ca.ulaval.glo4003.ultaxi.domain.geolocation.Geolocation;
import ca.ulaval.glo4003.ultaxi.domain.transportrequest.TransportRequest;
import ca.ulaval.glo4003.ultaxi.domain.transportrequest.TransportRequestAssignator;
import ca.ulaval.glo4003.ultaxi.domain.transportrequest.TransportRequestRepository;
import ca.ulaval.glo4003.ultaxi.domain.transportrequest.TransportRequestSearchQueryBuilder;
import ca.ulaval.glo4003.ultaxi.domain.user.UserRepository;
import ca.ulaval.glo4003.ultaxi.domain.user.exception.EmptySearchResultsException;
import ca.ulaval.glo4003.ultaxi.domain.vehicle.VehicleType;
import ca.ulaval.glo4003.ultaxi.infrastructure.transportrequest.TransportRequestSearchQueryBuilderInMemory;
import ca.ulaval.glo4003.ultaxi.transfer.transportrequest.TransportRequestAssembler;
import ca.ulaval.glo4003.ultaxi.transfer.transportrequest.TransportRequestDto;
import ca.ulaval.glo4003.ultaxi.transfer.transportrequest.TransportRequestSearchParameters;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class TransportRequestServiceTest {

    private static final String A_CLIENT_USERNAME = "ClientUsername";

    @Mock
    private TransportRequest transportRequest;
    @Mock
    private TransportRequestDto transportRequestDto;
    @Mock
    private TransportRequestRepository transportRequestRepository;
    @Mock
    private TransportRequestAssembler transportRequestAssembler;
    @Mock
    private UserRepository userRepository;
    @Mock
    private TransportRequestAssignator transportRequestAssignator;
    @Mock
    private TransportRequestSearchQueryBuilder transportRequestSearchQueryBuilder;
    @Mock
    private TransportRequestSearchParameters transportRequestSearchParameters;

    private TransportRequestService transportRequestService;

    @Before
    public void setUp() throws Exception {
        transportRequestService = new TransportRequestService(transportRequestRepository, transportRequestAssembler, userRepository, transportRequestAssignator);
    }

    @Test
    public void givenAValidTransportRequest_whenSendRequest_thenRequestIsAdded() {
        willReturn(transportRequest).given(transportRequestAssembler).create(transportRequestDto);

        transportRequestService.sendRequest(transportRequestDto, A_CLIENT_USERNAME);

        verify(transportRequestRepository).save(transportRequest);
    }

    @Test(expected = EmptySearchResultsException.class)
    public void givenAValidSearchQueryWithNoTransportRequestAssociated_whenSearching_thenThrowsException() {
        willReturn(transportRequestSearchQueryBuilder).given(transportRequestRepository).searchTransportRequests();
        willReturn(transportRequestSearchQueryBuilder).given(transportRequestSearchQueryBuilder).withVehicleType
            (anyString());
        willThrow(new EmptySearchResultsException("No results found.")).given(transportRequestSearchQueryBuilder)
            .findAll();

        transportRequestService.searchBy(transportRequestSearchParameters);
    }

    @Test
    public void
    givenAvailableTransportRequests_whenSearching_thenReturnsTransportRequestsAssociatedWithDriverVehicleType() {
        String typeOfVehicleBeingSearched = "Car";
        willReturn(typeOfVehicleBeingSearched).given(transportRequestSearchParameters).getVehicleType();
        willReturn(new TransportRequestSearchQueryBuilderInMemory(givenTransportRequests())).given
            (transportRequestRepository)
            .searchTransportRequests();

        List<TransportRequestDto> transportRequestDtos = transportRequestService.searchBy
            (transportRequestSearchParameters);

        assertEquals(2, transportRequestDtos.size());
    }

    private Map<String, TransportRequest> givenTransportRequests() {
        Map<String, TransportRequest> transportRequests = new HashMap<>();
        transportRequests.put("1", new TransportRequest("ClientA", new Geolocation(25.0, 26.0), "Note", VehicleType
            .CAR));
        transportRequests.put("2", new TransportRequest("ClientB", new Geolocation(35.0, 36.0), "Note", VehicleType
            .VAN));
        transportRequests.put("3", new TransportRequest("ClientC", new Geolocation(45.0, 46.0), "Note", VehicleType
            .CAR));
        return transportRequests;
    }
}
