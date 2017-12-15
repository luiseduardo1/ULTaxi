package ca.ulaval.glo4003.ultaxi.service.transportrequest;

import ca.ulaval.glo4003.ultaxi.domain.geolocation.Geolocation;
import ca.ulaval.glo4003.ultaxi.domain.messaging.MessagingTaskProducer;
import ca.ulaval.glo4003.ultaxi.domain.messaging.messagingtask.MessagingTask;
import ca.ulaval.glo4003.ultaxi.domain.messaging.sms.SmsSender;
import ca.ulaval.glo4003.ultaxi.domain.search.exception.EmptySearchResultsException;
import ca.ulaval.glo4003.ultaxi.domain.transportrequest.TransportRequest;
import ca.ulaval.glo4003.ultaxi.domain.transportrequest.TransportRequestRepository;
import ca.ulaval.glo4003.ultaxi.domain.transportrequest.TransportRequestSearchQueryBuilder;
import ca.ulaval.glo4003.ultaxi.domain.transportrequest.TransportRequestStatus;
import ca.ulaval.glo4003.ultaxi.domain.user.PhoneNumber;
import ca.ulaval.glo4003.ultaxi.domain.user.User;
import ca.ulaval.glo4003.ultaxi.domain.user.UserRepository;
import ca.ulaval.glo4003.ultaxi.domain.user.driver.Driver;
import ca.ulaval.glo4003.ultaxi.domain.vehicle.Vehicle;
import ca.ulaval.glo4003.ultaxi.domain.vehicle.VehicleType;
import ca.ulaval.glo4003.ultaxi.infrastructure.transportrequest.TransportRequestSearchQueryBuilderInMemory;
import ca.ulaval.glo4003.ultaxi.service.user.UserAuthenticationService;
import ca.ulaval.glo4003.ultaxi.transfer.transportrequest.TransportRequestAssembler;
import ca.ulaval.glo4003.ultaxi.transfer.transportrequest.TransportRequestDto;
import ca.ulaval.glo4003.ultaxi.transfer.transportrequest.TransportRequestTotalAmountAssembler;
import ca.ulaval.glo4003.ultaxi.utils.distancecalculator.DistanceCalculatorStrategy;
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
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class TransportRequestServiceTest {

    private static final String A_USERNAME = "Username";
    private static final String A_VALID_TOKEN = "Valid token";
    private static final String A_VALID_PHONE_NUMBER = "4354322323";
    private static final String A_VALID_DRIVER_TOKEN = "Driver token";
    private static final VehicleType CAR_VEHICULE_TYPE = VehicleType.CAR;
    private static final String A_TRANSPORT_REQUEST_ID = "Transport request Id";

    @Mock
    private TransportRequest transportRequest;
    @Mock
    private TransportRequestDto transportRequestDto;
    @Mock
    private TransportRequestRepository transportRequestRepository;
    @Mock
    private TransportRequestAssembler transportRequestAssembler;
    @Mock
    private TransportRequestTotalAmountAssembler transportRequestTotalAmountAssembler;
    @Mock
    private DistanceCalculatorStrategy distanceCalculatorStrategy;
    @Mock
    private TransportRequestSearchQueryBuilder transportRequestSearchQueryBuilder;
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserAuthenticationService userAuthenticationService;
    @Mock
    private MessagingTaskProducer messagingTaskProducer;
    @Mock
    private SmsSender smsSender;
    @Mock
    private Driver driver;
    @Mock
    private Vehicle vehicle;
    @Mock
    private User user;
    @Mock
    private PhoneNumber phoneNumber;

    private TransportRequestService transportRequestService;

    @Before
    public void setUp() {
        transportRequestService = new TransportRequestService(transportRequestRepository, transportRequestAssembler,
                                                              userRepository, userAuthenticationService, messagingTaskProducer,
                                                              smsSender, transportRequestTotalAmountAssembler, distanceCalculatorStrategy);
        willReturn(driver).given(userAuthenticationService).getUserFromToken(A_VALID_DRIVER_TOKEN);
        willReturn(user).given(userAuthenticationService).getUserFromToken(A_VALID_TOKEN);
        willReturn(A_USERNAME).given(transportRequest).getClientUsername();
        willReturn(user).given(userRepository).findByUsername(A_USERNAME);
        willReturn(vehicle).given(driver).getVehicle();
        willReturn(phoneNumber).given(user).getPhoneNumber();
        willReturn(A_VALID_PHONE_NUMBER).given(phoneNumber).getNumber();
        willReturn(CAR_VEHICULE_TYPE).given(driver).getVehicleType();
        willReturn(A_TRANSPORT_REQUEST_ID).given(driver).getCurrentTransportRequestId();
        willReturn(transportRequest).given(transportRequestRepository).findById(A_TRANSPORT_REQUEST_ID);
    }

    @Test
    public void givenAValidTransportRequest_whenSendRequest_thenRequestIsAdded() {
        willReturn(transportRequest).given(transportRequestAssembler).create(transportRequestDto);

        transportRequestService.sendRequest(transportRequestDto, A_VALID_TOKEN);

        verify(transportRequestRepository).save(transportRequest);
    }

    @Test(expected = EmptySearchResultsException.class)
    public void givenAValidSearchQueryWithNoTransportRequestAssociated_whenSearching_thenThrowsException() {
        willReturn(transportRequestSearchQueryBuilder).given(transportRequestRepository).searchTransportRequests();
        willReturn(transportRequestSearchQueryBuilder).given(transportRequestSearchQueryBuilder).withVehicleType
            (anyString());
        willThrow(new EmptySearchResultsException("No results found.")).given(transportRequestSearchQueryBuilder)
            .findAll();

        transportRequestService.searchAvailableTransportRequests(A_VALID_DRIVER_TOKEN);
    }

    @Test
    public void
    givenAvailableTransportRequests_whenSearching_thenReturnsTransportRequestsAssociatedWithDriverVehicleType() {
        willReturn(new TransportRequestSearchQueryBuilderInMemory(givenTransportRequests())).given
            (transportRequestRepository)
            .searchTransportRequests();

        List<TransportRequestDto> transportRequestDtos = transportRequestService.searchAvailableTransportRequests(
            A_VALID_DRIVER_TOKEN);

        assertEquals(2, transportRequestDtos.size());
    }

    @Test
    public void
    givenADriverArrivedAtStartingPosition_whenNotifyingDriverHasArrived_thenTransportRequestStatusIsModified() {
        transportRequestService.notifyDriverHasArrived(A_VALID_DRIVER_TOKEN);

        verify(transportRequest).updateStatus(TransportRequestStatus.ARRIVED);
    }

    @Test
    public void givenADriverArrivedAtStartingPosition_whenNotifyingDriverHasArrived_thenMessagingTaskProducerIsCalled
        () {
        transportRequestService.notifyDriverHasArrived(A_VALID_DRIVER_TOKEN);

        verify(messagingTaskProducer).send(any(MessagingTask.class));
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
