package ca.ulaval.glo4003.ws.api.vehicle;

import ca.ulaval.glo4003.ws.api.vehicle.dto.VehicleDto;
import ca.ulaval.glo4003.ws.domain.vehicle.InvalidVehicleTypeException;
import ca.ulaval.glo4003.ws.domain.vehicle.VehicleAlreadyExistsException;
import ca.ulaval.glo4003.ws.domain.vehicle.VehicleService;

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
        } catch(VehicleAlreadyExistsException | InvalidVehicleTypeException) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}
