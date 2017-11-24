package ca.ulaval.glo4003.ultaxi.transfer.transportrequest;

import ca.ulaval.glo4003.ultaxi.domain.geolocation.Geolocation;
import ca.ulaval.glo4003.ultaxi.domain.transportrequest.TransportRequest;

public class TransportRequestAssembler {

    public TransportRequest create(TransportRequestDto transportRequestDto) {
        Geolocation startingPosition = new Geolocation(transportRequestDto.getStartingPositionLatitude(),
                                                       transportRequestDto.getStartingPositionLongitude());
        TransportRequest transportRequest = new TransportRequest();
        transportRequest.setStartingPosition(startingPosition);
        transportRequest.setNote(transportRequestDto.getNote());
        transportRequest.setVehicleType(transportRequestDto.getVehicleType());
        return transportRequest;
    }

    public TransportRequestDto create(TransportRequest transportRequest) {
        TransportRequestDto transportRequestDto = new TransportRequestDto();
        transportRequestDto.setStartingPositionLatitude(transportRequest.getStartingPosition().getLatitude());
        transportRequestDto.setStartingPositionLongitude(transportRequest.getStartingPosition().getLongitude());
        transportRequestDto.setNote(transportRequest.getNote());
        transportRequestDto.setVehicleType(transportRequest.getVehicleType().name());
        transportRequestDto.setClientUsername(transportRequest.getClientUsername());
        return transportRequestDto;
    }
}
