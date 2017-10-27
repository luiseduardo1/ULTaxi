package ca.ulaval.glo4003.ultaxi.api.vehicle;

import ca.ulaval.glo4003.ultaxi.domain.user.exception.NonExistentUserException;
import ca.ulaval.glo4003.ultaxi.domain.vehicle.exception.InvalidVehicleAssociationException;
import ca.ulaval.glo4003.ultaxi.domain.vehicle.exception.InvalidVehicleTypeException;
import ca.ulaval.glo4003.ultaxi.domain.vehicle.exception.NonExistentVehicleException;
import ca.ulaval.glo4003.ultaxi.domain.vehicle.exception.VehicleAlreadyExistsException;
import ca.ulaval.glo4003.ultaxi.service.vehicle.VehicleService;
import ca.ulaval.glo4003.ultaxi.transfer.vehicle.VehicleAssociationDto;
import ca.ulaval.glo4003.ultaxi.transfer.vehicle.VehicleDto;

import javax.ws.rs.core.Response;

//TODO:Securize
public class VehicleResourceImpl implements VehicleResource {

    private final VehicleService vehicleService;

    public VehicleResourceImpl(VehicleService vehicleService) {
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
    public Response associateVehicle(VehicleAssociationDto vehicleAssociationDto) {
        try {
            vehicleService.associateVehicle(vehicleAssociationDto);
            return Response.ok().build();
        } catch (NonExistentUserException |
             NonExistentVehicleException |
            InvalidVehicleAssociationException exception) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @Override
    public Response dissociateVehicle(VehicleAssociationDto vehicleAssociationDto) {
        try {
            vehicleService.disassociateVehicle(vehicleAssociationDto);
            return Response.ok().build();
        } catch (InvalidVehicleAssociationException |
            NonExistentVehicleException | NonExistentUserException
            exception) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}
