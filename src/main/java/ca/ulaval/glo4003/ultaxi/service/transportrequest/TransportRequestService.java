package ca.ulaval.glo4003.ultaxi.service.transportrequest;

import ca.ulaval.glo4003.ultaxi.domain.transportrequest.TransportRequest;
import ca.ulaval.glo4003.ultaxi.domain.transportrequest.TransportRequestRepository;
import ca.ulaval.glo4003.ultaxi.transfer.transportrequest.TransportRequestAssembler;
import ca.ulaval.glo4003.ultaxi.transfer.transportrequest.TransportRequestDto;

public class TransportRequestService {

    private TransportRequestRepository transportRequestRepository;
    private TransportRequestAssembler transportRequestAssembler;

    public TransportRequestService(TransportRequestRepository transportRequestRepository, TransportRequestAssembler
        transportRequestAssembler) {
        this.transportRequestRepository = transportRequestRepository;
        this.transportRequestAssembler = transportRequestAssembler;
    }

    public String sendRequest(TransportRequestDto transportRequestDto) {
        TransportRequest transportRequest = transportRequestAssembler.create(transportRequestDto);
        transportRequestRepository.save(transportRequest);
        return transportRequest.getId();
    }

}
