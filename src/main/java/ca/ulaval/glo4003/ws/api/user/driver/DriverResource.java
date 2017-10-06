package ca.ulaval.glo4003.ws.api.user.driver;

import ca.ulaval.glo4003.ws.transfer.user.driver.DriverDto;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/drivers")
public interface DriverResource {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    Response createDriver(DriverDto driverDto);

}
