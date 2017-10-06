package ca.ulaval.glo4003.ws.domain.transportrequest;

import ca.ulaval.glo4003.ws.api.transportrequest.dto.TransportRequestDto;

import java.util.logging.Logger;

public class TransportRequestService {

    private TransportRequestRepository transportRequestRepository;
    private TransportRequestAssembler transportRequestAssembler;

    public TransportRequestService(TransportRequestRepository transportRequestRepository, TransportRequestAssembler transportRequestAssembler) {
        this.transportRequestRepository = transportRequestRepository;
        this.transportRequestAssembler = transportRequestAssembler;
    }

    public void sendRequest(TransportRequestDto transportRequestDto) {
        TransportRequest transportRequest = transportRequestAssembler.create(transportRequestDto);
        transportRequestRepository.save(transportRequest);
    }

}
