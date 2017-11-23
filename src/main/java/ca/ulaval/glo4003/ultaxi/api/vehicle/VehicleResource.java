package ca.ulaval.glo4003.ultaxi.api.vehicle;

import ca.ulaval.glo4003.ultaxi.domain.user.Role;
import ca.ulaval.glo4003.ultaxi.http.authentication.filtering.Secured;
import ca.ulaval.glo4003.ultaxi.transfer.vehicle.VehicleAssociationDto;
import ca.ulaval.glo4003.ultaxi.transfer.vehicle.VehicleDto;
import ca.ulaval.glo4003.ultaxi.transfer.vehicle.VehiculeUpdateDistanceRateDTO;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/vehicles")
@Secured(Role.ADMINISTRATOR)
public interface VehicleResource {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    Response createVehicle(VehicleDto vehicleDto);

    @POST
    @Path("/update/rate/distance")
    @Consumes(MediaType.APPLICATION_JSON)
    Response updateDistanceRate(VehiculeUpdateDistanceRateDTO vehiculeUpdateDistanceRateDTO);

    @POST
    @Path("/associate")
    @Consumes(MediaType.APPLICATION_JSON)
    Response associateVehicle(VehicleAssociationDto vehicleAssociationDto);

    @POST
    @Path("/dissociate")
    @Consumes(MediaType.WILDCARD)
    Response dissociateVehicle(String username);
}
