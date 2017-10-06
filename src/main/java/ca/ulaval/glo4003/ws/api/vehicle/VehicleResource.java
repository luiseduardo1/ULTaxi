package ca.ulaval.glo4003.ws.api.vehicle;

import ca.ulaval.glo4003.ws.api.vehicle.dto.VehicleDto;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/vehicles")
public interface VehicleResource {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    Response createVehicle(VehicleDto vehicleDto);
}
