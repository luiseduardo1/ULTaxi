package ca.ulaval.glo4003.ultaxi.api.user.driver;

import ca.ulaval.glo4003.ultaxi.api.middleware.authentication.Secured;
import ca.ulaval.glo4003.ultaxi.domain.user.Role;
import ca.ulaval.glo4003.ultaxi.transfer.user.driver.DriverDto;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/drivers")
public interface DriverResource {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured(Role.Administrator)
    Response createDriver(DriverDto driverDto);

}
