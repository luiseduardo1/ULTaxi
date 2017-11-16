package ca.ulaval.glo4003.ultaxi.api.transportrequest;

import ca.ulaval.glo4003.ultaxi.domain.geolocation.exception.InvalidGeolocationException;
import ca.ulaval.glo4003.ultaxi.domain.transportrequest.exception.InvalidTransportRequestAssignationException;
import ca.ulaval.glo4003.ultaxi.domain.transportrequest.exception.NonExistentTransportRequestException;
import ca.ulaval.glo4003.ultaxi.domain.user.Role;
import ca.ulaval.glo4003.ultaxi.domain.user.User;
import ca.ulaval.glo4003.ultaxi.domain.user.driver.Driver;
import ca.ulaval.glo4003.ultaxi.domain.user.exception.EmptySearchResultsException;
import ca.ulaval.glo4003.ultaxi.domain.user.exception.NonExistentUserException;
import ca.ulaval.glo4003.ultaxi.domain.vehicle.exception.InvalidVehicleTypeException;
import ca.ulaval.glo4003.ultaxi.http.authentication.filtering.Secured;
import ca.ulaval.glo4003.ultaxi.service.transportrequest.TransportRequestService;
import ca.ulaval.glo4003.ultaxi.transfer.transportrequest.TransportRequestDto;

import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import java.util.List;

public class TransportRequestResourceImpl implements TransportRequestResource {

    private TransportRequestService transportRequestService;

    public TransportRequestResourceImpl(TransportRequestService transportRequestService) {
        this.transportRequestService = transportRequestService;
    }

    @Override
    @Secured({Role.CLIENT})
    public Response sendTransportRequest(String clientToken, TransportRequestDto transportRequestDto) {
        User user = userAuthenticationService.authenticateFromToken(clientToken);
        String transportRequestId = transportRequestService.sendRequest(transportRequestDto, user.getUsername());
        return Response.status(Response.Status.CREATED).entity(transportRequestId).build();
    }

    @Override
    @Secured({Role.DRIVER})
    public Response searchAvailableTransportRequests(String driverToken) {
        Driver driver = (Driver) userAuthenticationService.authenticateFromToken(driverToken);
        TransportRequestSearchParameters searchParameters =
            new TransportRequestSearchParameters(driver.getVehicleType().name());

        GenericEntity<List<TransportRequestDto>> availableTransportRequests =
            new GenericEntity<List<TransportRequestDto>>(transportRequestService.searchBy(searchParameters)) {
            };

        return Response.ok(availableTransportRequests).build();
    }

    @Override
    @Secured({Role.DRIVER})
    public Response assignTransportRequest(String driverToken, String transportRequestId) {
        try {
            transportRequestService.assignTransportRequest(driverToken, transportRequestId);
            return Response.ok().build();
        } catch (NonExistentUserException | InvalidTransportRequestAssignationException |
                NonExistentTransportRequestException exception) {
            return Response.status(Response.Status.BAD_REQUEST).entity(exception.getMessage()).build();
        }

    }
}
