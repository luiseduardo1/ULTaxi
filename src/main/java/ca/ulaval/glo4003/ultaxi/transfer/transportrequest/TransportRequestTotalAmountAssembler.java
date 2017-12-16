package ca.ulaval.glo4003.ultaxi.transfer.transportrequest;

import ca.ulaval.glo4003.ultaxi.domain.transportrequest.TransportRequest;

public class TransportRequestTotalAmountAssembler {

    public TransportRequestTotalAmountDto create(TransportRequest transportRequest) {
        TransportRequestTotalAmountDto transportRequestTotalAmountDto = new TransportRequestTotalAmountDto();
        transportRequestTotalAmountDto.setTotalAmount(transportRequest.getTotalAmount().getValue());
        return transportRequestTotalAmountDto;
    }
}
