package ca.ulaval.glo4003.ws.domain.request;

import ca.ulaval.glo4003.ws.api.request.dto.RequestDto;

import java.util.logging.Logger;

public class RequestService {

    private Logger logger = Logger.getLogger(RequestService.class.getName());
    private RequestRepository requestRepository;
    private RequestAssembler requestAssembler;

    public RequestService(RequestRepository requestRepository, RequestAssembler requestAssembler) {
        this.requestRepository = requestRepository;
        this.requestAssembler = requestAssembler;
    }

    public void sendTransportRequest(RequestDto requestDto) {
        logger.info("Add new transport request");
        Request request = requestAssembler.create(requestDto);
        requestRepository.save(request);
    }
}
