package ca.ulaval.glo4003.ultaxi.api.transportrequest;

import ca.ulaval.glo4003.ultaxi.domain.user.Role;
import ca.ulaval.glo4003.ultaxi.domain.user.User;
import ca.ulaval.glo4003.ultaxi.domain.user.driver.Driver;
import ca.ulaval.glo4003.ultaxi.http.authentication.filtering.Secured;
import ca.ulaval.glo4003.ultaxi.service.transportrequest.TransportRequestService;
import ca.ulaval.glo4003.ultaxi.service.user.UserAuthenticationService;
import ca.ulaval.glo4003.ultaxi.transfer.transportrequest.TransportRequestDto;
import ca.ulaval.glo4003.ultaxi.transfer.transportrequest.TransportRequestSearchParameters;

import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import java.util.List;

public class TransportRequestResourceImpl implements TransportRequestResource {

    private TransportRequestService transportRequestService;
    private UserAuthenticationService userAuthenticationService;

    public TransportRequestResourceImpl(TransportRequestService transportRequestService, UserAuthenticationService
        userAuthenticationService) {
        this.transportRequestService = transportRequestService;
        this.userAuthenticationService = userAuthenticationService;
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
    public Response notifyHasArrived(String driverToken) {
        Driver driver = (Driver) userAuthenticationService.authenticateFromToken(driverToken);
        transportRequestService.notifyDriverHasArrived(driver);
        return Response.ok().build();
    }
}
