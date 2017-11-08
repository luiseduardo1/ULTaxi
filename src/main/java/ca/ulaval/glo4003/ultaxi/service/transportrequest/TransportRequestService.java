package ca.ulaval.glo4003.ultaxi.service.transportrequest;

import ca.ulaval.glo4003.ultaxi.domain.transportrequest.TransportRequest;
import ca.ulaval.glo4003.ultaxi.domain.transportrequest.TransportRequestRepository;
import ca.ulaval.glo4003.ultaxi.domain.user.UserRepository;
import ca.ulaval.glo4003.ultaxi.domain.user.driver.Driver;
import ca.ulaval.glo4003.ultaxi.service.user.UserAuthenticationService;
import ca.ulaval.glo4003.ultaxi.transfer.transportrequest.TransportRequestAssembler;
import ca.ulaval.glo4003.ultaxi.transfer.transportrequest.TransportRequestDto;
import ca.ulaval.glo4003.ultaxi.transfer.transportrequest.TransportRequestSearchParameters;

import java.util.List;
import java.util.stream.Collectors;

public class TransportRequestService {

    private final TransportRequestRepository transportRequestRepository;
    private final TransportRequestAssembler transportRequestAssembler;
    private final UserRepository userRepository;

    public TransportRequestService(TransportRequestRepository transportRequestRepository, TransportRequestAssembler
            transportRequestAssembler, UserRepository userRepository) {
        this.transportRequestRepository = transportRequestRepository;
        this.transportRequestAssembler = transportRequestAssembler;
        this.userRepository = userRepository;
    }

    public String sendRequest(TransportRequestDto transportRequestDto, String clientUsername) {
        TransportRequest transportRequest = transportRequestAssembler.create(transportRequestDto);
        transportRequest.setClientUsername(clientUsername);
        transportRequestRepository.save(transportRequest);
        return transportRequest.getId();
    }

    public List<TransportRequestDto> searchBy(TransportRequestSearchParameters requestTransportSearchParameters) {
        return this.transportRequestRepository
                .searchTransportRequests()
                .withVehicleType(requestTransportSearchParameters.getVehicleType())
                .findAll()
                .stream()
                .map(transportRequestAssembler::create)
                .collect(Collectors.toList());
    }

    public void assignTransportRequest(String transportRequestAssignationId, String driverUserName) {
        Driver driver = (Driver) userRepository.findByUsername(driverUserName);
        TransportRequest transportRequest = transportRequestRepository.findById(transportRequestAssignationId);
        driver.assignTransportRequest(transportRequest);
        userRepository.put(driver);
        transportRequestRepository.update(transportRequest);
    }
}
