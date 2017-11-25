package ca.ulaval.glo4003.ultaxi.api.user.driver;

import ca.ulaval.glo4003.ultaxi.domain.user.Role;
import ca.ulaval.glo4003.ultaxi.http.authentication.filtering.Secured;
import ca.ulaval.glo4003.ultaxi.service.user.driver.DriverService;
import ca.ulaval.glo4003.ultaxi.transfer.user.driver.DriverDto;
import ca.ulaval.glo4003.ultaxi.transfer.user.driver.DriverSearchParameters;

import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import java.util.List;

public class DriverResourceImpl implements DriverResource {

    private final DriverService driverService;

    public DriverResourceImpl(DriverService driverService) {
        this.driverService = driverService;
    }

    @Override
    @Secured({Role.ADMINISTRATOR})
    public Response createDriver(DriverDto driverDto) {
        driverService.addDriver(driverDto);
        return Response.ok().build();
    }

    @Override
    @Secured({Role.ADMINISTRATOR})
    public Response searchBy(String socialInsuranceNumber, String firstName, String lastName) {
        DriverSearchParameters searchParameters =
            new DriverSearchParameters(socialInsuranceNumber, firstName, lastName);

        GenericEntity<List<DriverDto>> drivers =
            new GenericEntity<List<DriverDto>>(driverService.searchBy(searchParameters)) {
            };
        return Response.ok(drivers).build();
    }
}
