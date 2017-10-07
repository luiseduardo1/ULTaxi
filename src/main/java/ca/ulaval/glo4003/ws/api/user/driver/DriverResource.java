package ca.ulaval.glo4003.ws.api.user.driver;

import ca.ulaval.glo4003.ws.api.middleware.authentification.Secured;
import ca.ulaval.glo4003.ws.api.user.driver.dto.DriverDto;
import ca.ulaval.glo4003.ws.domain.user.Role;

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
