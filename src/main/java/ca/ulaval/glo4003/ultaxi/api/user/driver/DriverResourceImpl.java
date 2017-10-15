package ca.ulaval.glo4003.ultaxi.api.user.driver;

import ca.ulaval.glo4003.ultaxi.domain.user.exception.EmptySearchResultsException;
import ca.ulaval.glo4003.ultaxi.domain.user.exception.InvalidPhoneNumberException;
import ca.ulaval.glo4003.ultaxi.domain.user.exception.InvalidSinException;
import ca.ulaval.glo4003.ultaxi.domain.user.exception.InvalidUserNameException;
import ca.ulaval.glo4003.ultaxi.domain.user.exception.UserAlreadyExistsException;
import ca.ulaval.glo4003.ultaxi.service.user.driver.DriverService;
import ca.ulaval.glo4003.ultaxi.transfer.user.driver.DriverDto;
import ca.ulaval.glo4003.ultaxi.transfer.user.driver.DriverSearchParameters;

import javax.ws.rs.core.Response;
import java.util.List;

public class DriverResourceImpl implements DriverResource {

    private DriverService driverService;

    public DriverResourceImpl(DriverService driverService) {
        this.driverService = driverService;
    }

    @Override
    public Response createDriver(DriverDto driverDto) {
        try {
            driverService.addDriver(driverDto);
            return Response.ok().build();
        } catch (UserAlreadyExistsException | InvalidUserNameException |
            InvalidPhoneNumberException | InvalidSinException exception) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @Override
    public Response searchBy(String sin, String firstName, String lastName) {
        DriverSearchParameters searchParameters = new DriverSearchParameters(sin, firstName, lastName);
        try {
            List<DriverDto> drivers = driverService.searchBy(searchParameters);
            return Response.ok(drivers).build();
        } catch (EmptySearchResultsException exception) {
            return Response.status(Response.Status.NOT_FOUND).entity(exception.getMessage()).build();
        }
    }
}
