package ca.ulaval.glo4003.ws.service.vehicle;

import ca.ulaval.glo4003.ws.domain.vehicle.Vehicle;
import ca.ulaval.glo4003.ws.domain.vehicle.VehicleRepository;
import ca.ulaval.glo4003.ws.transfer.vehicle.VehicleAssembler;
import ca.ulaval.glo4003.ws.transfer.vehicle.VehicleDto;

import java.util.logging.Logger;

public class VehicleService {

    private Logger logger = Logger.getLogger(VehicleService.class.getName());

    private VehicleRepository vehicleRepository;
    private VehicleAssembler vehicleAssembler;

    public VehicleService(VehicleRepository vehicleRepository, VehicleAssembler vehicleAssembler) {
        this.vehicleRepository = vehicleRepository;
        this.vehicleAssembler = vehicleAssembler;
    }

    public void addVehicle(VehicleDto vehicleDto) {
        logger.info(String.format("Add new vehicle %s", vehicleDto));
        Vehicle vehicle = vehicleAssembler.create(vehicleDto);
        vehicleRepository.save(vehicle);
    }
}
