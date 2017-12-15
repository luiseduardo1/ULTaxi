package ca.ulaval.glo4003.ultaxi.service.transportrequest;

import ca.ulaval.glo4003.ultaxi.domain.messaging.MessagingTaskProducer;
import ca.ulaval.glo4003.ultaxi.domain.messaging.messagingtask.MessagingTask;
import ca.ulaval.glo4003.ultaxi.domain.messaging.messagingtask.SendDriverHasArrivedSmsTask;
import ca.ulaval.glo4003.ultaxi.domain.messaging.sms.SmsSender;
import ca.ulaval.glo4003.ultaxi.domain.transportrequest.TransportRequest;
import ca.ulaval.glo4003.ultaxi.domain.transportrequest.TransportRequestRepository;
import ca.ulaval.glo4003.ultaxi.domain.transportrequest.TransportRequestStatus;
import ca.ulaval.glo4003.ultaxi.domain.user.User;
import ca.ulaval.glo4003.ultaxi.domain.user.UserRepository;
import ca.ulaval.glo4003.ultaxi.domain.user.driver.Driver;
import ca.ulaval.glo4003.ultaxi.domain.vehicle.exception.NonExistentVehicleException;
import ca.ulaval.glo4003.ultaxi.service.user.UserAuthenticationService;
import ca.ulaval.glo4003.ultaxi.transfer.transportrequest.TransportRequestAssembler;
import ca.ulaval.glo4003.ultaxi.transfer.transportrequest.TransportRequestDto;
import ca.ulaval.glo4003.ultaxi.transfer.user.UserPersistenceAssembler;
import ca.ulaval.glo4003.ultaxi.transfer.user.UserPersistenceDto;

import java.util.List;
import java.util.stream.Collectors;

public class TransportRequestService {

    private final TransportRequestRepository transportRequestRepository;
    private final TransportRequestAssembler transportRequestAssembler;
    private final UserRepository userRepository;
    private final UserAuthenticationService userAuthenticationService;
    private final MessagingTaskProducer messagingTaskProducer;
    private final SmsSender smsSender;
    private final UserPersistenceAssembler userPersistenceAssembler;

    public TransportRequestService(TransportRequestRepository transportRequestRepository,
                                   TransportRequestAssembler transportRequestAssembler, UserRepository userRepository,
                                   UserAuthenticationService userAuthenticationService,
                                   MessagingTaskProducer messagingTaskProducer, SmsSender smsSender,
                                   UserPersistenceAssembler userPersistenceAssembler) {
        this.transportRequestRepository = transportRequestRepository;
        this.transportRequestAssembler = transportRequestAssembler;
        this.userRepository = userRepository;
        this.userAuthenticationService = userAuthenticationService;
        this.messagingTaskProducer = messagingTaskProducer;
        this.smsSender = smsSender;
        this.userPersistenceAssembler = userPersistenceAssembler;
    }

    public String sendRequest(TransportRequestDto transportRequestDto, String clientToken) {
        UserPersistenceDto user = userAuthenticationService.getUserFromToken(clientToken);
        TransportRequest transportRequest = transportRequestAssembler.create(transportRequestDto);
        transportRequest.setClientUsername(user.getUsername());
        transportRequestRepository.save(transportRequest);
        return transportRequest.getId();
    }

    public List<TransportRequestDto> searchAvailableTransportRequests(String driverToken) {
        UserPersistenceDto userPersistenceDto = userAuthenticationService.getUserFromToken(driverToken);
        Driver driver = (Driver) userPersistenceAssembler.create(userPersistenceDto);
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
        UserPersistenceDto userPersistenceDto = userAuthenticationService.getUserFromToken(driverToken);
        Driver driver = (Driver) userPersistenceAssembler.create(userPersistenceDto);
        TransportRequest transportRequest = transportRequestRepository.findById(transportRequestId);
        driver.assignTransportRequestId(transportRequest);
        UserPersistenceDto userPersistenceDtoAssigned = userPersistenceAssembler.create(driver);
        userRepository.update(userPersistenceDtoAssigned);
        transportRequestRepository.update(transportRequest);
    }

    public void notifyDriverHasArrived(String driverToken) {
        UserPersistenceDto userPersistenceDto = userAuthenticationService.getUserFromToken(driverToken);
        Driver driver = (Driver) userPersistenceAssembler.create(userPersistenceDto);
        TransportRequest transportRequest = transportRequestRepository.findById(driver.getCurrentTransportRequestId());
        transportRequest.updateStatus(TransportRequestStatus.ARRIVED);
        transportRequestRepository.update(transportRequest);

        UserPersistenceDto user = userRepository.findByUsername(transportRequest.getClientUsername());
        MessagingTask messagingTask = new SendDriverHasArrivedSmsTask(user.getPhoneNumber().getNumber(),
                                                                      smsSender,
                                                                      transportRequest.getId(),
                                                                      transportRequestRepository,
                                                                      userRepository);
        messagingTaskProducer.send(messagingTask);
    }

}
