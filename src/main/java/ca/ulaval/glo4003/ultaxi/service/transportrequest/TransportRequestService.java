package ca.ulaval.glo4003.ultaxi.service.transportrequest;

import ca.ulaval.glo4003.ultaxi.domain.messaging.MessagingTaskProducer;
import ca.ulaval.glo4003.ultaxi.domain.messaging.messagingtask.MessagingTask;
import ca.ulaval.glo4003.ultaxi.domain.messaging.messagingtask.SendDriverHasArrivedSmsTask;
import ca.ulaval.glo4003.ultaxi.domain.messaging.sms.SmsSender;
import ca.ulaval.glo4003.ultaxi.domain.transportrequest.TransportRequest;
import ca.ulaval.glo4003.ultaxi.domain.transportrequest.TransportRequestRepository;
import ca.ulaval.glo4003.ultaxi.domain.transportrequest.TransportRequestStatus;
import ca.ulaval.glo4003.ultaxi.domain.transportrequest.exception.ClientAlreadyHasAnActiveTransportRequestException;
import ca.ulaval.glo4003.ultaxi.domain.user.User;
import ca.ulaval.glo4003.ultaxi.domain.user.UserRepository;
import ca.ulaval.glo4003.ultaxi.domain.user.client.Client;
import ca.ulaval.glo4003.ultaxi.domain.user.driver.Driver;
import ca.ulaval.glo4003.ultaxi.domain.vehicle.exception.NonExistentVehicleException;
import ca.ulaval.glo4003.ultaxi.service.user.UserAuthenticationService;
import ca.ulaval.glo4003.ultaxi.transfer.transportrequest.TransportRequestAssembler;
import ca.ulaval.glo4003.ultaxi.transfer.transportrequest.TransportRequestDto;

import java.util.List;
import java.util.stream.Collectors;

public class TransportRequestService {

    private final TransportRequestRepository transportRequestRepository;
    private final TransportRequestAssembler transportRequestAssembler;
    private final UserRepository userRepository;
    private final UserAuthenticationService userAuthenticationService;
    private final MessagingTaskProducer messagingTaskProducer;
    private final SmsSender smsSender;

    public TransportRequestService(TransportRequestRepository transportRequestRepository,
        TransportRequestAssembler transportRequestAssembler, UserRepository userRepository,
        UserAuthenticationService userAuthenticationService,
        MessagingTaskProducer messagingTaskProducer, SmsSender smsSender) {
        this.transportRequestRepository = transportRequestRepository;
        this.transportRequestAssembler = transportRequestAssembler;
        this.userRepository = userRepository;
        this.userAuthenticationService = userAuthenticationService;
        this.messagingTaskProducer = messagingTaskProducer;
        this.smsSender = smsSender;
    }

    public String sendRequest(TransportRequestDto transportRequestDto, String clientToken) {
        Client client = (Client) userAuthenticationService.getUserFromToken(clientToken);
        if(client.hasAnActiveTransportRequest()) {
            throw new ClientAlreadyHasAnActiveTransportRequestException(
                String.format("Client %s already has an active transport request.", client.getUsername())
            );
        }
        TransportRequest transportRequest = transportRequestAssembler.create(transportRequestDto);
        transportRequest.setClientUsername(client.getUsername());
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
