package ca.ulaval.glo4003.ultaxi.api.vehicle;

import ca.ulaval.glo4003.ultaxi.domain.user.Role;
import ca.ulaval.glo4003.ultaxi.http.authentication.filtering.Secured;
import ca.ulaval.glo4003.ultaxi.transfer.vehicle.VehicleAssociationDto;
import ca.ulaval.glo4003.ultaxi.transfer.vehicle.VehicleDto;

import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

//TODO:TO-SECURE
@Path("/vehicles")
public interface VehicleResource {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    Response createVehicle(VehicleDto vehicleDto);

    @POST
    @Path("/associate")
    @Consumes(MediaType.APPLICATION_JSON)
    Response associateVehicle(@HeaderParam(value = "Authorization") String token,
                              VehicleAssociationDto vehicleAssociationDto);

}
