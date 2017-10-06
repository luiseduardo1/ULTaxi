package ca.ulaval.glo4003.ws.api.user.driver;

import ca.ulaval.glo4003.ws.api.user.driver.dto.DriverDto;
import ca.ulaval.glo4003.ws.domain.user.driver.DriverService;
import ca.ulaval.glo4003.ws.domain.user.exception.InvalidPhoneNumberException;
import ca.ulaval.glo4003.ws.domain.user.exception.InvalidSinException;
import ca.ulaval.glo4003.ws.domain.user.exception.InvalidUserNameException;
import ca.ulaval.glo4003.ws.domain.user.exception.UserAlreadyExistsException;

import javax.ws.rs.core.Response;

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
}
