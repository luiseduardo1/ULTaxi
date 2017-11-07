package ca.ulaval.glo4003.ultaxi.service.vehicle;

import ca.ulaval.glo4003.ultaxi.domain.user.Role;
import ca.ulaval.glo4003.ultaxi.domain.user.User;
import ca.ulaval.glo4003.ultaxi.domain.user.UserRepository;
import ca.ulaval.glo4003.ultaxi.domain.user.driver.Driver;
import ca.ulaval.glo4003.ultaxi.domain.vehicle.Vehicle;
import ca.ulaval.glo4003.ultaxi.domain.vehicle.VehicleRepository;
import ca.ulaval.glo4003.ultaxi.domain.vehicle.exception.InvalidVehicleAssociationException;
import ca.ulaval.glo4003.ultaxi.transfer.vehicle.VehicleAssembler;
import ca.ulaval.glo4003.ultaxi.transfer.vehicle.VehicleAssociationDto;
import ca.ulaval.glo4003.ultaxi.transfer.vehicle.VehicleDto;

import java.util.function.BiConsumer;
import java.util.logging.Logger;

public class VehicleService {

    private final Logger logger = Logger.getLogger(VehicleService.class.getName());

    private final VehicleRepository vehicleRepository;
    private final VehicleAssembler vehicleAssembler;
    private final UserRepository userRepository;


    public VehicleService(VehicleRepository vehicleRepository,
        VehicleAssembler vehicleAssembler,
        UserRepository userRepository) {
        this.vehicleRepository = vehicleRepository;
        this.vehicleAssembler = vehicleAssembler;
        this.userRepository = userRepository;
    }

    public void addVehicle(VehicleDto vehicleDto) {
        logger.info(String.format("Add new vehicle %s", vehicleDto));
        Vehicle vehicle = vehicleAssembler.create(vehicleDto);
        vehicleRepository.save(vehicle);
    }

    public void associateVehicle(VehicleAssociationDto vehicleAssociationDto) {
        withVehicleAssociationAction(
            vehicleAssociationDto,
            "Vehicle association for driver %s and vehicle %s",
            Driver::associateVehicle
        );
    }

    public void dissociateVehicle(VehicleAssociationDto vehicleAssociationDto) {
        withVehicleAssociationAction(
            vehicleAssociationDto,
            "Vehicle dissociation for driver %s and vehicle",
            Driver::dissociateVehicle
        );
    }

    private void withVehicleAssociationAction(VehicleAssociationDto vehicleAssociationDto, String logMessage,
        BiConsumer<Driver, Vehicle> associationAction) {
        if (vehicleAssociationDto == null) {
            throw new InvalidVehicleAssociationException("The given vehicle association is null.");
        }

        logger.info(String.format(
            logMessage,
            vehicleAssociationDto.getUsername(),
            vehicleAssociationDto.getRegistrationNumber()
        ));
        User user = userRepository.findByUsername(vehicleAssociationDto.getUsername());
        Vehicle vehicle = vehicleRepository.findByRegistrationNumber(vehicleAssociationDto.getRegistrationNumber());

        validateAssociationEntities(vehicle, user);
        associationAction.accept((Driver) user, vehicle);

        userRepository.update(user);
        vehicleRepository.update(vehicle);
    }

    private void validateAssociationEntities(Vehicle vehicle, User user) {
        if (user == null || vehicle == null || user.getRole() != Role.DRIVER) {
            throw new InvalidVehicleAssociationException("Can't associate this vehicle with the given driver. Verify " +
                                                             "that the driver and the vehicle exist.");
        }
    }
}
