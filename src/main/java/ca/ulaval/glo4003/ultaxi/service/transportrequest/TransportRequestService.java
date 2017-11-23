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
import ca.ulaval.glo4003.ultaxi.service.user.UserService;
import ca.ulaval.glo4003.ultaxi.transfer.transportrequest.TransportRequestAssembler;
import ca.ulaval.glo4003.ultaxi.transfer.transportrequest.TransportRequestDto;
import ca.ulaval.glo4003.ultaxi.transfer.transportrequest.TransportRequestSearchParameters;

import java.util.List;
import java.util.stream.Collectors;

public class TransportRequestService {

    private final TransportRequestRepository transportRequestRepository;
    private final TransportRequestAssembler transportRequestAssembler;
    private final UserRepository userRepository;
    private final UserService userService;
    private final MessagingTaskProducer messagingTaskProducer;
    private final SmsSender smsSender;

    public TransportRequestService(TransportRequestRepository transportRequestRepository, TransportRequestAssembler
        transportRequestAssembler, UserRepository userRepository, UserService userService,
        MessagingTaskProducer messagingTaskProducer, SmsSender smsSender) {
        this.transportRequestRepository = transportRequestRepository;
        this.transportRequestAssembler = transportRequestAssembler;
        this.userRepository = userRepository;
        this.userService = userService;
        this.messagingTaskProducer = messagingTaskProducer;
        this.smsSender = smsSender;
    }

    public String sendRequest(TransportRequestDto transportRequestDto, String clientToken) {
        User user = userService.getUserFromToken(clientToken);
        TransportRequest transportRequest = transportRequestAssembler.create(transportRequestDto);
        transportRequest.setClientUsername(user.getUsername());
        transportRequestRepository.save(transportRequest);
        return transportRequest.getId();
    }

    public List<TransportRequestDto> searchBy(String driverToken) {
        Driver driver = (Driver) userService.getUserFromToken(driverToken);
        return this.transportRequestRepository
            .searchTransportRequests()
            .withVehicleType(driver.getVehicleType().name())
            .findAll()
            .stream()
            .map(transportRequestAssembler::create)
            .collect(Collectors.toList());
    }

    public void notifyDriverHasArrived(String driverToken) {
        Driver driver = (Driver) userService.getUserFromToken(driverToken);
        TransportRequest transportRequest = transportRequestRepository.findById(driver.getCurrentTransportRequestId());
        transportRequest.updateStatus(TransportRequestStatus.ARRIVED);
        transportRequestRepository.update(transportRequest);

        User user = userRepository.findByUsername(transportRequest.getClientUsername());
        MessagingTask messagingTask = new SendDriverHasArrivedSmsTask(user.getPhoneNumber(),
                                                                      smsSender,
                                                                      transportRequest.getId(),
                                                                      transportRequestRepository,
                                                                      userRepository);
        messagingTaskProducer.send(messagingTask);
    }

}
