package ca.ulaval.glo4003.ultaxi.api.vehicle;

import ca.ulaval.glo4003.ultaxi.domain.user.Role;
import ca.ulaval.glo4003.ultaxi.domain.user.exception.NonExistentUserException;
import ca.ulaval.glo4003.ultaxi.domain.vehicle.exception.InvalidVehicleAssociationException;
import ca.ulaval.glo4003.ultaxi.domain.vehicle.exception.InvalidVehicleDissociationException;
import ca.ulaval.glo4003.ultaxi.domain.vehicle.exception.InvalidVehicleTypeException;
import ca.ulaval.glo4003.ultaxi.domain.vehicle.exception.NonExistentVehicleException;
import ca.ulaval.glo4003.ultaxi.domain.vehicle.exception.VehicleAlreadyExistsException;
import ca.ulaval.glo4003.ultaxi.http.authentication.filtering.Secured;
import ca.ulaval.glo4003.ultaxi.service.vehicle.VehicleService;
import ca.ulaval.glo4003.ultaxi.transfer.vehicle.VehicleAssociationDto;
import ca.ulaval.glo4003.ultaxi.transfer.vehicle.VehicleDto;

import javax.ws.rs.core.Response;

@Secured(Role.ADMINISTRATOR)
public class VehicleResourceImpl implements VehicleResource {

    private final VehicleService vehicleService;

    public VehicleResourceImpl(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @Override
    @Secured(Role.ADMINISTRATOR)
    public Response createVehicle(VehicleDto vehicleDto) throws InvalidVehicleTypeException,
        VehicleAlreadyExistsException {
        vehicleService.addVehicle(vehicleDto);
        return Response.ok().build();
    }

    @Override
    public Response associateVehicle(VehicleAssociationDto vehicleAssociationDto) throws NonExistentUserException,
        InvalidVehicleAssociationException, NonExistentVehicleException {
        vehicleService.associateVehicle(vehicleAssociationDto);
        return Response.ok().build();

    }

    @Override
    public Response dissociateVehicle(String username) throws NonExistentUserException,
        InvalidVehicleDissociationException, NonExistentVehicleException {
        vehicleService.dissociateVehicle(username);
        return Response.ok().build();
    }
}
