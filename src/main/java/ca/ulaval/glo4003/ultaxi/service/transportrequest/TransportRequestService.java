package ca.ulaval.glo4003.ultaxi.service.transportrequest;

import ca.ulaval.glo4003.ultaxi.domain.geolocation.Geolocation;
import ca.ulaval.glo4003.ultaxi.domain.geolocation.exception.InvalidGeolocationException;
import ca.ulaval.glo4003.ultaxi.domain.messaging.MessagingTaskProducer;
import ca.ulaval.glo4003.ultaxi.domain.messaging.messagingtask.MessagingTask;
import ca.ulaval.glo4003.ultaxi.domain.messaging.messagingtask.SendDriverHasArrivedSmsTask;
import ca.ulaval.glo4003.ultaxi.domain.messaging.sms.SmsSender;
import ca.ulaval.glo4003.ultaxi.domain.rate.Rate;
import ca.ulaval.glo4003.ultaxi.domain.rate.RateRepository;
import ca.ulaval.glo4003.ultaxi.domain.search.exception.EmptySearchResultsException;
import ca.ulaval.glo4003.ultaxi.domain.transportrequest.TransportRequest;
import ca.ulaval.glo4003.ultaxi.domain.transportrequest.TransportRequestRepository;
import ca.ulaval.glo4003.ultaxi.domain.transportrequest.exception.ClientAlreadyHasAnActiveTransportRequestException;
import ca.ulaval.glo4003.ultaxi.domain.transportrequest.exception.DriverHasNoTransportRequestAssignedException;
import ca.ulaval.glo4003.ultaxi.domain.transportrequest.exception.InvalidTransportRequestAssignationException;
import ca.ulaval.glo4003.ultaxi.domain.transportrequest.exception.InvalidTransportRequestStatusException;
import ca.ulaval.glo4003.ultaxi.domain.transportrequest.exception.NonExistentTransportRequestException;
import ca.ulaval.glo4003.ultaxi.domain.user.User;
import ca.ulaval.glo4003.ultaxi.domain.user.UserRepository;
import ca.ulaval.glo4003.ultaxi.domain.user.client.Client;
import ca.ulaval.glo4003.ultaxi.domain.user.driver.Driver;
import ca.ulaval.glo4003.ultaxi.domain.user.exception.NonExistentUserException;
import ca.ulaval.glo4003.ultaxi.domain.vehicle.exception.InvalidVehicleTypeException;
import ca.ulaval.glo4003.ultaxi.domain.vehicle.exception.NonExistentVehicleException;
import ca.ulaval.glo4003.ultaxi.infrastructure.user.jwt.exception.InvalidTokenException;
import ca.ulaval.glo4003.ultaxi.service.user.UserAuthenticationService;
import ca.ulaval.glo4003.ultaxi.transfer.rate.RatePersistenceAssembler;
import ca.ulaval.glo4003.ultaxi.transfer.rate.RatePersistenceDto;
import ca.ulaval.glo4003.ultaxi.transfer.transportrequest.TransportRequestAssembler;
import ca.ulaval.glo4003.ultaxi.transfer.transportrequest.TransportRequestCompleteDto;
import ca.ulaval.glo4003.ultaxi.transfer.transportrequest.TransportRequestDto;
import ca.ulaval.glo4003.ultaxi.transfer.transportrequest.TransportRequestTotalAmountAssembler;
import ca.ulaval.glo4003.ultaxi.transfer.transportrequest.TransportRequestTotalAmountDto;

import java.util.List;
import java.util.stream.Collectors;

public class TransportRequestService {

    private final TransportRequestRepository transportRequestRepository;
    private final TransportRequestAssembler transportRequestAssembler;
    private final TransportRequestTotalAmountAssembler transportRequestTotalAmountAssembler;
    private final UserRepository userRepository;
    private final UserAuthenticationService userAuthenticationService;
    private final MessagingTaskProducer messagingTaskProducer;
    private final SmsSender smsSender;
    private final RateRepository rateRepository;
    private final RatePersistenceAssembler ratePersistenceAssembler;

    public TransportRequestService(TransportRequestRepository transportRequestRepository, TransportRequestAssembler
            transportRequestAssembler, UserRepository userRepository,
                                   UserAuthenticationService userAuthenticationService,
                                   MessagingTaskProducer messagingTaskProducer, SmsSender smsSender,
                                   TransportRequestTotalAmountAssembler transportRequestTotalAmountAssembler,
                                   RateRepository rateRepository,
                                   RatePersistenceAssembler ratePersistenceAssembler) {
        this.transportRequestRepository = transportRequestRepository;
        this.transportRequestAssembler = transportRequestAssembler;
        this.userRepository = userRepository;
        this.userAuthenticationService = userAuthenticationService;
        this.messagingTaskProducer = messagingTaskProducer;
        this.smsSender = smsSender;
        this.transportRequestTotalAmountAssembler = transportRequestTotalAmountAssembler;
        this.rateRepository = rateRepository;
        this.ratePersistenceAssembler = ratePersistenceAssembler;
    }

    public String sendRequest(TransportRequestDto transportRequestDto, String clientToken) throws
        InvalidGeolocationException, InvalidVehicleTypeException, InvalidTokenException,
        ClientAlreadyHasAnActiveTransportRequestException {
        Client client = (Client) userAuthenticationService.getUserFromToken(clientToken);
        TransportRequest transportRequest = transportRequestAssembler.create(transportRequestDto);
        transportRequest.setClientUsername(client.getUsername());
        client.assignTransportRequestId(transportRequest.getId());
        transportRequestRepository.save(transportRequest);
        userRepository.update(client);
        return transportRequest.getId();
    }

    public List<TransportRequestDto> searchAvailableTransportRequests(String driverToken) throws
        EmptySearchResultsException, InvalidTokenException {
        Driver driver = (Driver) userAuthenticationService.getUserFromToken(driverToken);
        if (driver.getVehicleType() == null) {
            throw new NonExistentVehicleException("There is no vehicle associated to this driver.");
        }
        return this.transportRequestRepository
            .searchTransportRequests()
            .withVehicleType(driver.getVehicleType().name())
            .findAll()
            .stream()
            .map(transportRequestAssembler::create)
            .collect(Collectors.toList());

    }

    public void assignTransportRequest(String driverToken, String transportRequestId) throws
        InvalidTransportRequestAssignationException, NonExistentTransportRequestException, NonExistentUserException,
        InvalidTokenException {
        Driver driver = (Driver) userAuthenticationService.getUserFromToken(driverToken);
        TransportRequest transportRequest = transportRequestRepository.findById(transportRequestId);

        driver.assignTransportRequestId(transportRequest);

        userRepository.update(driver);
        transportRequestRepository.update(transportRequest);
    }

    public TransportRequestTotalAmountDto notifyRideHasCompleted(
            String driverToken, TransportRequestCompleteDto transportRequestCompleteDto) {
        Driver driver = (Driver) userAuthenticationService.getUserFromToken(driverToken);
        TransportRequest transportRequest = transportRequestRepository
                .findById(driver.getCurrentTransportRequestId());

        transportRequest.setToCompleted(driver,new Geolocation(transportRequestCompleteDto.getEndingPositionLatitude(),
                transportRequestCompleteDto.getEndingPositionLongitude()));

        RatePersistenceDto ratePersistenceDto = rateRepository
                .findDistanceRateByVehicleType(transportRequest.getVehicleType());
        Rate rate = ratePersistenceAssembler.create(ratePersistenceDto);
        transportRequest.calculateTotalAmount(rate);

        userRepository.update(driver);
        transportRequestRepository.update(transportRequest);

        return transportRequestTotalAmountAssembler.create(transportRequest);
    }

    public void notifyDriverHasArrived(String driverToken) throws DriverHasNoTransportRequestAssignedException,
        InvalidTransportRequestStatusException, NonExistentTransportRequestException, InvalidTokenException {
        Driver driver = (Driver) userAuthenticationService.getUserFromToken(driverToken);
        TransportRequest transportRequest = transportRequestRepository.findById(driver.getCurrentTransportRequestId());
        transportRequest.setToArrived();
        transportRequestRepository.update(transportRequest);
        User user = userRepository.findByUsername(transportRequest.getClientUsername());
        MessagingTask messagingTask = new SendDriverHasArrivedSmsTask(user.getPhoneNumber().getNumber(),
                smsSender,
                transportRequest.getId(),
                transportRequestRepository,
                userRepository);
        messagingTaskProducer.send(messagingTask);

    }

    public void notifyRideHasStarted(String driverToken) throws InvalidTokenException,
        NonExistentTransportRequestException, InvalidTransportRequestStatusException {
        Driver driver = (Driver) userAuthenticationService.getUserFromToken(driverToken);
        TransportRequest transportRequest = transportRequestRepository.findById(driver.getCurrentTransportRequestId());
        transportRequest.setToStarted();
        transportRequestRepository.update(transportRequest);
    }
}
