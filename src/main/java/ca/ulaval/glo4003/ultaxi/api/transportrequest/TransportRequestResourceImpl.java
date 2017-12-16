package ca.ulaval.glo4003.ultaxi.api.transportrequest;

import ca.ulaval.glo4003.ultaxi.domain.geolocation.exception.InvalidGeolocationException;
import ca.ulaval.glo4003.ultaxi.domain.search.exception.EmptySearchResultsException;
import ca.ulaval.glo4003.ultaxi.domain.transportrequest.exception.ClientAlreadyHasAnActiveTransportRequestException;
import ca.ulaval.glo4003.ultaxi.domain.transportrequest.exception.DriverHasNoTransportRequestAssignedException;
import ca.ulaval.glo4003.ultaxi.domain.transportrequest.exception.InvalidTransportRequestAssignationException;
import ca.ulaval.glo4003.ultaxi.domain.transportrequest.exception.InvalidTransportRequestStatusException;
import ca.ulaval.glo4003.ultaxi.domain.transportrequest.exception.NonExistentTransportRequestException;
import ca.ulaval.glo4003.ultaxi.domain.user.Role;
import ca.ulaval.glo4003.ultaxi.domain.user.exception.NonExistentUserException;
import ca.ulaval.glo4003.ultaxi.domain.vehicle.exception.InvalidVehicleTypeException;
import ca.ulaval.glo4003.ultaxi.domain.vehicle.exception.NonExistentVehicleException;
import ca.ulaval.glo4003.ultaxi.http.authentication.filtering.Secured;
import ca.ulaval.glo4003.ultaxi.infrastructure.user.jwt.exception.InvalidTokenException;
import ca.ulaval.glo4003.ultaxi.service.transportrequest.TransportRequestService;
import ca.ulaval.glo4003.ultaxi.transfer.transportrequest.TransportRequestCompleteDto;
import ca.ulaval.glo4003.ultaxi.transfer.transportrequest.TransportRequestDto;
import ca.ulaval.glo4003.ultaxi.transfer.transportrequest.TransportRequestTotalAmountDto;

import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import java.util.List;

public class TransportRequestResourceImpl implements TransportRequestResource {

    private final TransportRequestService transportRequestService;

    public TransportRequestResourceImpl(TransportRequestService transportRequestService) {
        this.transportRequestService = transportRequestService;
    }

    @Override
    @Secured({Role.CLIENT})
    public Response sendTransportRequest(String clientToken, TransportRequestDto transportRequestDto) throws
        InvalidGeolocationException, InvalidVehicleTypeException, InvalidTokenException,
        ClientAlreadyHasAnActiveTransportRequestException {
        String transportRequestId = transportRequestService.sendRequest(transportRequestDto, clientToken);
        return Response.status(Response.Status.CREATED).entity(transportRequestId).build();
    }

    @Override
    @Secured({Role.DRIVER})
    public Response searchAvailableTransportRequests(String driverToken) throws EmptySearchResultsException,
        NonExistentVehicleException, InvalidTokenException {
        GenericEntity<List<TransportRequestDto>> availableTransportRequests =
            new GenericEntity<List<TransportRequestDto>>(transportRequestService.searchAvailableTransportRequests(
                driverToken)) {
            };
        return Response.ok(availableTransportRequests).build();
    }

    @Override
    @Secured({Role.DRIVER})
    public Response assignTransportRequest(String driverToken, String transportRequestId) throws
        InvalidTransportRequestAssignationException, NonExistentTransportRequestException, NonExistentUserException,
        InvalidTokenException {
        transportRequestService.assignTransportRequest(driverToken, transportRequestId);
        return Response.ok().build();
    }

    @Override
    @Secured({Role.DRIVER})
    public Response notifyHasArrived(String driverToken) throws DriverHasNoTransportRequestAssignedException,
        InvalidTransportRequestStatusException, NonExistentTransportRequestException, InvalidTokenException {
        transportRequestService.notifyDriverHasArrived(driverToken);
        return Response.ok().build();
    }

    @Override
    @Secured({Role.DRIVER})
    public Response notifyHasStarted(String driverToken) throws InvalidTransportRequestStatusException,
        NonExistentTransportRequestException, InvalidTokenException {
        transportRequestService.notifyRideHasStarted(driverToken);
        return Response.ok().build();
    }

    @Override
    @Secured({Role.DRIVER})
    public Response notifyHasCompleted(String driverToken,
                                             TransportRequestCompleteDto transportRequestCompleteDto) {
        TransportRequestTotalAmountDto transportRequestTotalAmountDto = transportRequestService
                .notifyRideHasCompleted(
                driverToken, transportRequestCompleteDto);
        return Response.ok().entity(transportRequestTotalAmountDto).build();
    }

}
