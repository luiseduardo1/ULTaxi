package ca.ulaval.glo4003.ultaxi.transfer.transportrequest;

import ca.ulaval.glo4003.ultaxi.domain.geolocation.Geolocation;
import ca.ulaval.glo4003.ultaxi.domain.money.Money;
import ca.ulaval.glo4003.ultaxi.domain.transportrequest.TransportRequest;

import java.math.BigDecimal;

public class TransportRequestTotalAmountAssembler {

    public TransportRequestTotalAmountDto create(TransportRequest transportRequest) {
        TransportRequestTotalAmountDto transportRequestTotalAmountDto = new TransportRequestTotalAmountDto();
        transportRequestTotalAmountDto.setTotalAmount(transportRequest.getTotalAmount().getValue());
        return transportRequestTotalAmountDto;
    }
}
