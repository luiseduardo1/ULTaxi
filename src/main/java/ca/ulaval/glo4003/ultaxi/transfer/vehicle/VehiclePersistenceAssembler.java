package ca.ulaval.glo4003.ultaxi.transfer.vehicle;

import ca.ulaval.glo4003.ultaxi.domain.vehicle.Vehicle;
import ca.ulaval.glo4003.ultaxi.domain.vehicle.VehicleFactory;

public class VehiclePersistenceAssembler {

    public Vehicle create(VehiclePersistenceDto vehiclePersistenceDto) {
        return VehicleFactory.getVehicle(
            vehiclePersistenceDto.getVehicleType().name(),
            vehiclePersistenceDto.getColor(),
            vehiclePersistenceDto.getModel(),
            vehiclePersistenceDto.getRegistrationNumber());
    }

    public VehiclePersistenceDto create(Vehicle vehicle) {
        VehiclePersistenceDto vehiclePersistenceDto = new VehiclePersistenceDto();
        vehiclePersistenceDto.setVehicleType(vehicle.getType());
        vehiclePersistenceDto.setType(vehicle.getType().name());
        vehiclePersistenceDto.setColor(vehicle.getColor());
        vehiclePersistenceDto.setModel(vehicle.getModel());
        vehiclePersistenceDto.setRegistrationNumber(vehicle.getRegistrationNumber());
        return vehiclePersistenceDto;
    }
}
