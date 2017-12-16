package ca.ulaval.glo4003.ultaxi.service.vehicle;

import ca.ulaval.glo4003.ultaxi.domain.user.Role;
import ca.ulaval.glo4003.ultaxi.domain.user.User;
import ca.ulaval.glo4003.ultaxi.domain.user.UserRepository;
import ca.ulaval.glo4003.ultaxi.domain.user.driver.Driver;
import ca.ulaval.glo4003.ultaxi.domain.user.exception.NonExistentUserException;
import ca.ulaval.glo4003.ultaxi.domain.vehicle.Vehicle;
import ca.ulaval.glo4003.ultaxi.domain.vehicle.VehicleRepository;
import ca.ulaval.glo4003.ultaxi.domain.vehicle.exception.InvalidVehicleAssociationException;
import ca.ulaval.glo4003.ultaxi.domain.vehicle.exception.InvalidVehicleDissociationException;
import ca.ulaval.glo4003.ultaxi.domain.vehicle.exception.InvalidVehicleTypeException;
import ca.ulaval.glo4003.ultaxi.domain.vehicle.exception.NonExistentVehicleException;
import ca.ulaval.glo4003.ultaxi.domain.vehicle.exception.VehicleAlreadyExistsException;
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


    public VehicleService(VehicleRepository vehicleRepository,
                          VehicleAssembler vehicleAssembler,
                          UserRepository userRepository,
                          VehiclePersistenceAssembler vehiclePersistenceAssembler) {
        this.vehicleRepository = vehicleRepository;
        this.vehicleAssembler = vehicleAssembler;
        this.userRepository = userRepository;
        this.vehiclePersistenceAssembler = vehiclePersistenceAssembler;
    }

    public void addVehicle(VehicleDto vehicleDto) throws InvalidVehicleTypeException, VehicleAlreadyExistsException {
        logger.info(String.format("Add new vehicle %s", vehicleDto));
        Vehicle vehicle = vehicleAssembler.create(vehicleDto);
        VehiclePersistenceDto vehiclePersistenceDto = vehiclePersistenceAssembler.create(vehicle);
        vehicleRepository.save(vehiclePersistenceDto);
    }

    public void associateVehicle(VehicleAssociationDto vehicleAssociationDto) throws NonExistentUserException,
        InvalidVehicleAssociationException, NonExistentVehicleException {
        if (vehicleAssociationDto == null) {
            throw new InvalidVehicleAssociationException("The given vehicle association is null.");
        }
        logger.info(String.format(
            "Vehicle association for driver %s and vehicle %s",
            vehicleAssociationDto.getUsername(),
            vehicleAssociationDto.getRegistrationNumber()
        ));
        Driver driver = (Driver) userRepository.findByUsername(vehicleAssociationDto.getUsername());
        VehiclePersistenceDto vehiclePersistenceDto
            = vehicleRepository.findByRegistrationNumber(vehicleAssociationDto.getRegistrationNumber());

        validateAssociationEntities(vehiclePersistenceDto, driver);
        Vehicle vehicle = vehiclePersistenceAssembler.create(vehiclePersistenceDto);
        driver.associateVehicle(vehicle);
        VehiclePersistenceDto updatedVehiclePersistenceDto = vehiclePersistenceAssembler.create(vehicle);
        userRepository.update(driver);
        vehicleRepository.update(updatedVehiclePersistenceDto);
    }

    public void dissociateVehicle(String username) throws NonExistentUserException,
        InvalidVehicleDissociationException, NonExistentVehicleException {
        User user = Optional
            .ofNullable(username)
            .map(userRepository::findByUsername)
            .orElseThrow(() -> new InvalidVehicleDissociationException("Can't dissociate: the given username is " +
                "invalid."));
        if (user.getRole() != Role.DRIVER) {
            throw new InvalidVehicleDissociationException("Can't dissociate: The given user is not a driver.");
        }
        logger.info(String.format("Dissociating user %s and his vehicle.", username));
        Driver driver = (Driver) user;
        Vehicle vehicle = driver.getVehicle();
        driver.dissociateVehicle();
        VehiclePersistenceDto updatedVehiclePersistenceDto = vehiclePersistenceAssembler.create(vehicle);
        userRepository.update(user);
        vehicleRepository.update(updatedVehiclePersistenceDto);
    }

    private void validateAssociationEntities(VehiclePersistenceDto vehiclePersistenceDto, User user) {
        if (user == null || vehiclePersistenceDto == null || user.getRole() != Role.DRIVER) {
            throw new InvalidVehicleAssociationException("Can't associate this vehicle with the given driver. Verify " +
                "that the driver and the vehicle exist.");
        }
    }
}
