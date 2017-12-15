package ca.ulaval.glo4003.ultaxi.service.transportrequest;

import ca.ulaval.glo4003.ultaxi.domain.geolocation.Geolocation;
import ca.ulaval.glo4003.ultaxi.domain.messaging.MessagingTaskProducer;
import ca.ulaval.glo4003.ultaxi.domain.messaging.messagingtask.MessagingTask;
import ca.ulaval.glo4003.ultaxi.domain.messaging.messagingtask.SendDriverHasArrivedSmsTask;
import ca.ulaval.glo4003.ultaxi.domain.messaging.sms.SmsSender;
import ca.ulaval.glo4003.ultaxi.domain.rate.Rate;
import ca.ulaval.glo4003.ultaxi.domain.rate.RateFactory;
import ca.ulaval.glo4003.ultaxi.domain.rate.RateRepository;
import ca.ulaval.glo4003.ultaxi.domain.transportrequest.TransportRequest;
import ca.ulaval.glo4003.ultaxi.domain.transportrequest.TransportRequestRepository;
import ca.ulaval.glo4003.ultaxi.domain.transportrequest.TransportRequestStatus;
import ca.ulaval.glo4003.ultaxi.domain.user.User;
import ca.ulaval.glo4003.ultaxi.domain.user.UserRepository;
import ca.ulaval.glo4003.ultaxi.domain.user.driver.Driver;
import ca.ulaval.glo4003.ultaxi.domain.vehicle.exception.NonExistentVehicleException;
import ca.ulaval.glo4003.ultaxi.service.user.UserAuthenticationService;
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

    public TransportRequestService(TransportRequestRepository transportRequestRepository, TransportRequestAssembler
            transportRequestAssembler, UserRepository userRepository,
                                   UserAuthenticationService userAuthenticationService,
                                   MessagingTaskProducer messagingTaskProducer, SmsSender smsSender,
                                   TransportRequestTotalAmountAssembler transportRequestTotalAmountAssembler,
                                   RateRepository rateRepository) {
        this.transportRequestRepository = transportRequestRepository;
        this.transportRequestAssembler = transportRequestAssembler;
        this.userRepository = userRepository;
        this.userAuthenticationService = userAuthenticationService;
        this.messagingTaskProducer = messagingTaskProducer;
        this.smsSender = smsSender;
        this.transportRequestTotalAmountAssembler = transportRequestTotalAmountAssembler;
        this.rateRepository = rateRepository;
    }

    public String sendRequest(TransportRequestDto transportRequestDto, String clientToken) {
        User user = userAuthenticationService.getUserFromToken(clientToken);
        TransportRequest transportRequest = transportRequestAssembler.create(transportRequestDto);
        transportRequest.setClientUsername(user.getUsername());
        transportRequestRepository.save(transportRequest);
        return transportRequest.getId();
    }

    public List<TransportRequestDto> searchAvailableTransportRequests(String driverToken) {
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

    public void assignTransportRequest(String driverToken, String transportRequestId) {
        Driver driver = (Driver) userAuthenticationService.getUserFromToken(driverToken);
        TransportRequest transportRequest = transportRequestRepository.findById(transportRequestId);

        driver.assignTransportRequestId(transportRequest);

        userRepository.update(driver);
        transportRequestRepository.update(transportRequest);
    }

    public TransportRequestTotalAmountDto completeTransportRequest(
            String driverToken, TransportRequestCompleteDto transportRequestCompleteDto) {
        Driver driver = (Driver) userAuthenticationService.getUserFromToken(driverToken);
        TransportRequest transportRequest = transportRequestRepository
                .findById(transportRequestCompleteDto.getTransportRequestId());

        transportRequest.complete(driver,new Geolocation(transportRequestCompleteDto.getEndingPositionLatitude(),
                transportRequestCompleteDto.getEndingPositionLongitude()));

        Rate rate = RateFactory.getRate(transportRequest, rateRepository);
        transportRequest.calculateTotalAmount(rate);

        userRepository.update(driver);
        transportRequestRepository.update(transportRequest);

        return transportRequestTotalAmountAssembler.create(transportRequest);
    }

    public void notifyDriverHasArrived(String driverToken) {
        Driver driver = (Driver) userAuthenticationService.getUserFromToken(driverToken);
        TransportRequest transportRequest = transportRequestRepository.findById(driver.getCurrentTransportRequestId());
        transportRequest.updateStatus(TransportRequestStatus.ARRIVED);
        transportRequestRepository.update(transportRequest);

        User user = userRepository.findByUsername(transportRequest.getClientUsername());
        MessagingTask messagingTask = new SendDriverHasArrivedSmsTask(user.getPhoneNumber().getNumber(),
                smsSender,
                transportRequest.getId(),
                transportRequestRepository,
                userRepository);
        messagingTaskProducer.send(messagingTask);
    }

}
