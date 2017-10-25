package ca.ulaval.glo4003.ultaxi.api.vehicle;

import ca.ulaval.glo4003.ultaxi.domain.user.Role;
import ca.ulaval.glo4003.ultaxi.domain.vehicle.exception.InvalidVehicleTypeException;
import ca.ulaval.glo4003.ultaxi.domain.vehicle.exception.VehicleAlreadyExistsException;
import ca.ulaval.glo4003.ultaxi.http.authentication.filtering.Secured;
import ca.ulaval.glo4003.ultaxi.service.user.UserAuthenticationService;
import ca.ulaval.glo4003.ultaxi.service.vehicle.VehicleService;
import ca.ulaval.glo4003.ultaxi.transfer.vehicle.VehicleAssociationDto;
import ca.ulaval.glo4003.ultaxi.transfer.vehicle.VehicleDto;

import javax.ws.rs.HeaderParam;
import javax.ws.rs.core.Response;

//TODO:Securize
public class VehicleResourceImpl implements VehicleResource {

    private final VehicleService vehicleService;

    public VehicleResourceImpl(VehicleService vehicleService, UserAuthenticationService) {
        this.vehicleService = vehicleService;
    }

    @Override
    public Response createVehicle(VehicleDto vehicleDto) {
        try {
            vehicleService.addVehicle(vehicleDto);
            return Response.ok().build();
        } catch (VehicleAlreadyExistsException | InvalidVehicleTypeException exception) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @Override
    public Response associateVehicle(String token,
                                     VehicleAssociationDto vehicleAssociationDto) {


        return Response.ok().entity(vehicleAssociationDto).build();
    }
}
