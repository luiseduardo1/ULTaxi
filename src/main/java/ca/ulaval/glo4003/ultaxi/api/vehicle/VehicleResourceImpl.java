package ca.ulaval.glo4003.ultaxi.api.vehicle;

import ca.ulaval.glo4003.ultaxi.domain.user.Role;
import ca.ulaval.glo4003.ultaxi.http.authentication.filtering.Secured;
import ca.ulaval.glo4003.ultaxi.service.vehicle.VehicleService;
import ca.ulaval.glo4003.ultaxi.transfer.vehicle.VehicleAssociationDto;
import ca.ulaval.glo4003.ultaxi.transfer.vehicle.VehicleDto;
import ca.ulaval.glo4003.ultaxi.transfer.vehicle.VehiculeUpdateDistanceRateDTO;

import javax.ws.rs.core.Response;

@Secured(Role.ADMINISTRATOR)
public class VehicleResourceImpl implements VehicleResource {

    private final VehicleService vehiculeService;

    public VehicleResourceImpl(VehicleService vehiculeService) {
        this.vehiculeService = vehiculeService;
    }

    @Override
    public Response createVehicle(VehicleDto vehicleDto) {
        vehiculeService.addVehicle(vehicleDto);
        return Response.ok().build();
    }

    @Override
    public Response updateDistanceRate(VehiculeUpdateDistanceRateDTO vehiculeUpdateDistanceRateDTO) {
        vehiculeService.updateDistanceRate(vehiculeUpdateDistanceRateDTO);
        return Response.ok().build();
    }

    @Override
    public Response associateVehicle(VehicleAssociationDto vehicleAssociationDto) {
        vehiculeService.associateVehicle(vehicleAssociationDto);
        return Response.ok().build();
    }

    @Override
    public Response dissociateVehicle(String username) {
        vehiculeService.dissociateVehicle(username);
        return Response.ok().build();
    }
}
