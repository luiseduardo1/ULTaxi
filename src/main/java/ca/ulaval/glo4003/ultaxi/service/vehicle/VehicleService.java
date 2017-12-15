package ca.ulaval.glo4003.ultaxi.service.vehicle;

import ca.ulaval.glo4003.ultaxi.domain.user.Role;
import ca.ulaval.glo4003.ultaxi.domain.user.User;
import ca.ulaval.glo4003.ultaxi.domain.user.UserRepository;
import ca.ulaval.glo4003.ultaxi.domain.user.driver.Driver;
import ca.ulaval.glo4003.ultaxi.domain.vehicle.Vehicle;
import ca.ulaval.glo4003.ultaxi.domain.vehicle.VehicleRepository;
import ca.ulaval.glo4003.ultaxi.domain.vehicle.exception.InvalidVehicleAssociationException;
import ca.ulaval.glo4003.ultaxi.domain.vehicle.exception.InvalidVehicleDissociationException;
import ca.ulaval.glo4003.ultaxi.transfer.user.UserPersistenceAssembler;
import ca.ulaval.glo4003.ultaxi.transfer.user.UserPersistenceDto;
import ca.ulaval.glo4003.ultaxi.transfer.vehicle.VehicleAssembler;
import ca.ulaval.glo4003.ultaxi.transfer.vehicle.VehicleAssociationDto;
import ca.ulaval.glo4003.ultaxi.transfer.vehicle.VehicleDto;
import ca.ulaval.glo4003.ultaxi.transfer.vehicle.VehiclePersistenceAssembler;
import ca.ulaval.glo4003.ultaxi.transfer.vehicle.VehiclePersistenceDto;

import java.util.Optional;
import java.util.logging.Logger;

public class VehicleService {

    private final Logger logger = Logger.getLogger(VehicleService.class.getName());

    private final VehicleRepository vehicleRepository;
    private final VehicleAssembler vehicleAssembler;
    private final UserRepository userRepository;
    private final VehiclePersistenceAssembler vehiclePersistenceAssembler;
    private final UserPersistenceAssembler userPersistenceAssembler;


    public VehicleService(VehicleRepository vehicleRepository,
                          VehicleAssembler vehicleAssembler,
                          UserRepository userRepository,
                          VehiclePersistenceAssembler vehiclePersistenceAssembler,
                          UserPersistenceAssembler userPersistenceAssembler) {
        this.vehicleRepository = vehicleRepository;
        this.vehicleAssembler = vehicleAssembler;
        this.userRepository = userRepository;
        this.vehiclePersistenceAssembler = vehiclePersistenceAssembler;
        this.userPersistenceAssembler = userPersistenceAssembler;
    }

    public void addVehicle(VehicleDto vehicleDto) {
        logger.info(String.format("Add new vehicle %s", vehicleDto));
        Vehicle vehicle = vehicleAssembler.create(vehicleDto);
        VehiclePersistenceDto vehiclePersistenceDto = vehiclePersistenceAssembler.create(vehicle);
        vehicleRepository.save(vehiclePersistenceDto);
    }

    public void associateVehicle(VehicleAssociationDto vehicleAssociationDto) {
        if (vehicleAssociationDto == null) {
            throw new InvalidVehicleAssociationException("The given vehicle association is null.");
        }

        logger.info(String.format(
            "Vehicle association for driver %s and vehicle %s",
            vehicleAssociationDto.getUsername(),
            vehicleAssociationDto.getRegistrationNumber()
        ));

        UserPersistenceDto userPersistenceDto =
            userRepository.findByUsername(vehicleAssociationDto.getUsername());
        VehiclePersistenceDto vehiclePersistenceDto
            = vehicleRepository.findByRegistrationNumber(vehicleAssociationDto.getRegistrationNumber());
        Driver driver = (Driver) userPersistenceAssembler.create(userPersistenceDto);
        validateAssociationEntities(vehiclePersistenceDto, driver);
        Vehicle vehicle = vehiclePersistenceAssembler.create(vehiclePersistenceDto);
        driver.associateVehicle(vehicle);
        VehiclePersistenceDto updatedVehicle = vehiclePersistenceAssembler.create(vehicle);
        UserPersistenceDto updatedUser = userPersistenceAssembler.create(driver);
        userRepository.update(updatedUser);
        vehicleRepository.update(updatedVehicle);
    }

    public void dissociateVehicle(String username) {
        UserPersistenceDto user = Optional
            .ofNullable(username)
            .map(userRepository::findByUsername)
            .orElseThrow(() -> new InvalidVehicleDissociationException("Can't dissociate: the given username is " +
                "invalid."));
        if (user.getRole() != Role.DRIVER) {
            throw new InvalidVehicleDissociationException("Can't dissociate: The given user is not a driver.");
        }

        logger.info(String.format("Dissociating user %s and his vehicle.", username));
        UserPersistenceDto userPersistenceDto = user;
        Vehicle vehicle = userPersistenceDto.getVehicle();
        Driver driver = (Driver) userPersistenceAssembler.create(userPersistenceDto);
        driver.dissociateVehicle();

        VehiclePersistenceDto updatedVehicle = vehiclePersistenceAssembler.create(vehicle);
        UserPersistenceDto updatedUser = userPersistenceAssembler.create(driver);
        userRepository.update(updatedUser);
        vehicleRepository.update(updatedVehicle);
    }

    private void validateAssociationEntities(VehiclePersistenceDto vehicle, User user) {
        if (user == null || vehicle == null || user.getRole() != Role.DRIVER) {
            throw new InvalidVehicleAssociationException("Can't associate this vehicle with the given driver. Verify " +
                "that the driver and the vehicle exist.");
        }
    }
}
