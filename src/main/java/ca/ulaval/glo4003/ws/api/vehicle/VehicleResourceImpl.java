package ca.ulaval.glo4003.ws.api.vehicle;

import ca.ulaval.glo4003.ws.domain.vehicle.exception.InvalidVehicleTypeException;
import ca.ulaval.glo4003.ws.domain.vehicle.exception.VehicleAlreadyExistsException;
import ca.ulaval.glo4003.ws.service.vehicle.VehicleService;
import ca.ulaval.glo4003.ws.transfer.vehicle.VehicleDto;

import javax.ws.rs.core.Response;

public class VehicleResourceImpl implements VehicleResource {

    private VehicleService vehicleService;

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
}
