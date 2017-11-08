package ca.ulaval.glo4003.ultaxi.api.user.driver;

import ca.ulaval.glo4003.ultaxi.domain.user.Role;
import ca.ulaval.glo4003.ultaxi.http.authentication.filtering.Secured;
import ca.ulaval.glo4003.ultaxi.transfer.user.driver.DriverDto;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/drivers")
public interface DriverResource {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured({Role.ADMINISTRATOR})
    Response createDriver(DriverDto driverDto);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Secured({Role.ADMINISTRATOR})
    Response searchBy(@QueryParam("social-insurance-number") String socialInsuranceNumber,
        @QueryParam("first-name") String firstName,
        @QueryParam("last-name") String lastName);
}
