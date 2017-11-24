package ca.ulaval.glo4003.ultaxi.service.transportrequest;

import ca.ulaval.glo4003.ultaxi.domain.transportrequest.TransportRequest;
import ca.ulaval.glo4003.ultaxi.domain.transportrequest.TransportRequestRepository;
import ca.ulaval.glo4003.ultaxi.domain.user.User;
import ca.ulaval.glo4003.ultaxi.domain.user.UserRepository;
import ca.ulaval.glo4003.ultaxi.domain.user.driver.Driver;
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

    public TransportRequestService(TransportRequestRepository transportRequestRepository,
        TransportRequestAssembler transportRequestAssembler, UserRepository userRepository,
        UserAuthenticationService userAuthenticationService) {
        this.transportRequestRepository = transportRequestRepository;
        this.transportRequestAssembler = transportRequestAssembler;
        this.userRepository = userRepository;
        this.userAuthenticationService = userAuthenticationService;
    }

    public String sendRequest(TransportRequestDto transportRequestDto, String clientToken) {
        User user = userAuthenticationService.getUserFromToken(clientToken);
        TransportRequest transportRequest = transportRequestAssembler.create(transportRequestDto);
        transportRequest.setClientUsername(user.getUsername());
        transportRequestRepository.save(transportRequest);
        return transportRequest.getId();
    }

    public List<TransportRequestDto> searchBy(String driverToken) {
        Driver driver = (Driver) userAuthenticationService.getUserFromToken(driverToken);
        return this.transportRequestRepository
                .searchTransportRequests()
                .withVehicleType(driver.getVehicleType().name())
                .findAll()
                .stream()
                .map(transportRequestAssembler::create)
                .collect(Collectors.toList());
    }

    public void assignTransportRequest(String driverToken, String transportRequestAssignationId) {
        Driver driver = (Driver) userAuthenticationService.getUserFromToken(driverToken);
        TransportRequest transportRequest = transportRequestRepository.findById(transportRequestAssignationId);
        driver.assignTransportRequest(transportRequest);
        userRepository.update(driver);
        transportRequestRepository.save(transportRequest);
    }
}
