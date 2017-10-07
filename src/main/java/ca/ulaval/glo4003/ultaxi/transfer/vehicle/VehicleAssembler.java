package ca.ulaval.glo4003.ultaxi.transfer.vehicle;

import ca.ulaval.glo4003.ultaxi.domain.vehicle.Vehicle;
import ca.ulaval.glo4003.ultaxi.domain.vehicle.VehicleFactory;

public class VehicleAssembler {

    public Vehicle create(VehicleDto vehicleDto) {
        Vehicle vehicle = VehicleFactory.getVehicle(
            vehicleDto.getType(),
            vehicleDto.getColor(),
            vehicleDto.getModel(),
            vehicleDto.getRegistrationNumber()
        );
        return vehicle;
    }

    public VehicleDto create(Vehicle vehicle) {
        VehicleDto vehicleDto = new VehicleDto();
        vehicleDto.setType(vehicle.getType());
        vehicleDto.setColor(vehicle.getColor());
        vehicleDto.setModel(vehicle.getModel());
        vehicleDto.setRegistrationNumber(vehicle.getRegistrationNumber());
        return vehicleDto;
    }
}
