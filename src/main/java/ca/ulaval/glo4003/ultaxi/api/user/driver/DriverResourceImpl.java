package ca.ulaval.glo4003.ultaxi.api.user.driver;

import ca.ulaval.glo4003.ultaxi.domain.user.Role;
import ca.ulaval.glo4003.ultaxi.domain.user.exception.EmptySearchResultsException;
import ca.ulaval.glo4003.ultaxi.domain.user.exception.InvalidPhoneNumberException;
import ca.ulaval.glo4003.ultaxi.domain.user.exception.InvalidSocialInsuranceNumberException;
import ca.ulaval.glo4003.ultaxi.domain.user.exception.InvalidUserNameException;
import ca.ulaval.glo4003.ultaxi.domain.user.exception.UserAlreadyExistsException;
import ca.ulaval.glo4003.ultaxi.http.authentication.filtering.Secured;
import ca.ulaval.glo4003.ultaxi.service.user.driver.DriverService;
import ca.ulaval.glo4003.ultaxi.transfer.user.driver.DriverDto;
import ca.ulaval.glo4003.ultaxi.transfer.user.driver.DriverSearchParameters;

import javax.ws.rs.core.Response;
import java.util.List;

public class DriverResourceImpl implements DriverResource {

    private final DriverService driverService;

    public DriverResourceImpl(DriverService driverService) {
        this.driverService = driverService;
    }

    @Override
    @Secured(Role.Administrator)
    public Response createDriver(DriverDto driverDto) {
        try {
            driverService.addDriver(driverDto);
            return Response.ok().build();
        } catch (UserAlreadyExistsException | InvalidUserNameException |
            InvalidPhoneNumberException | InvalidSocialInsuranceNumberException exception) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @Override
    @Secured({Role.Administrator})
    public Response searchBy(String socialInsuranceNumber, String firstName, String lastName) {
        DriverSearchParameters searchParameters = new DriverSearchParameters(socialInsuranceNumber, firstName,
                                                                             lastName);
        try {
            List<DriverDto> drivers = driverService.searchBy(searchParameters);
            return Response.ok(drivers).build();
        } catch (EmptySearchResultsException exception) {
            return Response.status(Response.Status.NOT_FOUND).entity(exception.getMessage()).build();
        }
    }
}
