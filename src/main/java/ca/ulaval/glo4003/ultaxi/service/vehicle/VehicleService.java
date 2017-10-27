package ca.ulaval.glo4003.ultaxi.service.vehicle;

import ca.ulaval.glo4003.ultaxi.domain.user.User;
import ca.ulaval.glo4003.ultaxi.domain.user.UserRepository;
import ca.ulaval.glo4003.ultaxi.domain.user.driver.Driver;
import ca.ulaval.glo4003.ultaxi.domain.vehicle.Vehicle;
import ca.ulaval.glo4003.ultaxi.domain.vehicle.VehicleAssociator;
import ca.ulaval.glo4003.ultaxi.domain.vehicle.VehicleRepository;
import ca.ulaval.glo4003.ultaxi.transfer.vehicle.VehicleAssembler;
import ca.ulaval.glo4003.ultaxi.transfer.vehicle.VehicleAssociationDto;
import ca.ulaval.glo4003.ultaxi.transfer.vehicle.VehicleDto;

import java.util.logging.Logger;

public class VehicleService {

    private final Logger logger = Logger.getLogger(VehicleService.class.getName());

    private final VehicleRepository vehicleRepository;
    private final VehicleAssembler vehicleAssembler;
    private final VehicleAssociator vehicleAssociator;
    private final UserRepository userRepository;


    public VehicleService(VehicleRepository vehicleRepository,
                          VehicleAssembler vehicleAssembler,
                          VehicleAssociator vehicleAssociator,
                          UserRepository userRepository) {
        this.vehicleRepository = vehicleRepository;
        this.vehicleAssembler = vehicleAssembler;
        this.vehicleAssociator = vehicleAssociator;
        this.userRepository = userRepository;
    }

    public void addVehicle(VehicleDto vehicleDto) {
        logger.info(String.format("Add new vehicle %s", vehicleDto));
        Vehicle vehicle = vehicleAssembler.create(vehicleDto);
        vehicleRepository.save(vehicle);
    }

    public void associateVehicle(VehicleAssociationDto vehicleAssociationDto) {
        logger.info(String.format("Vehicule association for %s", vehicleAssociationDto));
        Driver driver = (Driver)userRepository.findByUsername(vehicleAssociationDto.getUserName());
        Vehicle vehicle = vehicleRepository.findByRegistrationNumber(
            vehicleAssociationDto.getRegistrationNumber());
        vehicleAssociator.associate(vehicle, driver);
        userRepository.put(driver);
        vehicleRepository.put(vehicle);
    }

    public void disassociateVehicle(VehicleAssociationDto vehicleAssociationDto) {
        logger.info(String.format("Vehicule dissacioation for %s", vehicleAssociationDto));
        User user = userRepository.findByUsername(vehicleAssociationDto.getUserName());
        Vehicle vehicle = vehicleRepository.findByRegistrationNumber(
            vehicleAssociationDto.getRegistrationNumber());
        vehicleAssociator.disassociate(vehicle, user);
        userRepository.put(user);
        vehicleRepository.put(vehicle);
    }
}
