package ca.ulaval.glo4003.ultaxi.api.transportrequest;

import ca.ulaval.glo4003.ultaxi.api.middleware.authentication.Secured;
import ca.ulaval.glo4003.ultaxi.domain.geolocation.exception.InvalidGeolocationException;
import ca.ulaval.glo4003.ultaxi.domain.user.Role;
import ca.ulaval.glo4003.ultaxi.domain.user.driver.Driver;
import ca.ulaval.glo4003.ultaxi.domain.user.exception.EmptySearchResultsException;
import ca.ulaval.glo4003.ultaxi.domain.user.exception.InvalidUserRoleException;
import ca.ulaval.glo4003.ultaxi.domain.vehicle.exception.InvalidVehicleTypeException;
import ca.ulaval.glo4003.ultaxi.service.transportrequest.TransportRequestService;
import ca.ulaval.glo4003.ultaxi.service.user.UserAuthenticationService;
import ca.ulaval.glo4003.ultaxi.transfer.transportrequest.TransportRequestDto;
import ca.ulaval.glo4003.ultaxi.transfer.transportrequest.TransportRequestSearchParameters;

import javax.ws.rs.core.Response;
import java.util.List;

public class TransportRequestResourceImpl implements TransportRequestResource {

    private TransportRequestService transportRequestService;
    private UserAuthenticationService userAuthenticationService;

    public TransportRequestResourceImpl(TransportRequestService transportRequestService, UserAuthenticationService userAuthenticationService) {
        this.transportRequestService = transportRequestService;
        this.userAuthenticationService = userAuthenticationService;
    }

    @Override
    @Secured({Role.Client})
    public Response sendTransportRequest(TransportRequestDto transportRequestDto) {
        try {
            String transportRequestId = transportRequestService.sendRequest(transportRequestDto);
            return Response.status(Response.Status.CREATED).entity(transportRequestId).build();
        } catch (InvalidVehicleTypeException | InvalidGeolocationException | InvalidUserRoleException exception) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @Override
    @Secured({Role.Driver})
    public Response searchAvailableTransportRequest(String token) {
        try {
            Driver driver = (Driver) userAuthenticationService.authenticateFromToken(token);
            TransportRequestSearchParameters searchParameters = new TransportRequestSearchParameters(driver.getVehicleType());
            List<TransportRequestDto> availableTransportRequests = transportRequestService.searchBy(searchParameters);
            return Response.ok(availableTransportRequests).build();
        } catch (EmptySearchResultsException exception) {
            return Response.status(Response.Status.NOT_FOUND).entity(exception.getMessage()).build();
        }
    }
}
