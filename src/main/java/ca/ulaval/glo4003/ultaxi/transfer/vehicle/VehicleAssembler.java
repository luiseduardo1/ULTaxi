package ca.ulaval.glo4003.ultaxi.transfer.vehicle;

import ca.ulaval.glo4003.ultaxi.domain.vehicle.Vehicle;
import ca.ulaval.glo4003.ultaxi.domain.vehicle.VehicleFactory;

public class VehicleAssembler {

    public Vehicle create(VehicleDto vehicleDto) {
        return VehicleFactory.getVehicle(
            vehicleDto.getType(),
            vehicleDto.getColor(),
            vehicleDto.getModel(),
            vehicleDto.getRegistrationNumber()
        );
    }

    public VehicleDto create(Vehicle vehicle) {
        VehicleDto vehicleDto = new VehicleDto();
        vehicleDto.setType(vehicle.getType().name());
        vehicleDto.setColor(vehicle.getColor());
        vehicleDto.setModel(vehicle.getModel());
        vehicleDto.setRegistrationNumber(vehicle.getRegistrationNumber());
        return vehicleDto;
    }
}
