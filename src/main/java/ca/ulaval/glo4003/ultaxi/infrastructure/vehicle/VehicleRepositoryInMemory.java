package ca.ulaval.glo4003.ultaxi.infrastructure.vehicle;

import ca.ulaval.glo4003.ultaxi.domain.vehicle.Vehicle;
import ca.ulaval.glo4003.ultaxi.domain.vehicle.VehicleRepository;
import ca.ulaval.glo4003.ultaxi.domain.vehicle.exception.NonExistentVehicleException;
import ca.ulaval.glo4003.ultaxi.domain.vehicle.exception.VehicleAlreadyExistsException;
import ca.ulaval.glo4003.ultaxi.transfer.vehicle.VehiclePersistenceDto;
import ca.ulaval.glo4003.ultaxi.utils.hashing.HashingStrategy;

import java.util.HashMap;
import java.util.Map;

public class VehicleRepositoryInMemory implements VehicleRepository {

    private final Map<String, VehiclePersistenceDto> vehicles = new HashMap<>();
    private final HashingStrategy hashingStrategy;

    public VehicleRepositoryInMemory(HashingStrategy hashingStrategy) {
        this.hashingStrategy = hashingStrategy;
    }


    @Override
    public VehiclePersistenceDto findByRegistrationNumber(String registrationNumber) {
        String formattedRegistrationNumber = registrationNumber.toUpperCase().trim();
        return vehicles.get(hashingStrategy.hash(formattedRegistrationNumber));
    }

    @Override
    public void save(VehiclePersistenceDto vehicle) {
        String registrationNumber = vehicle.getRegistrationNumber().toUpperCase().trim();
        String hashedRegistrationNumber = hashingStrategy.hash(registrationNumber);
        if (vehicles.containsKey(hashedRegistrationNumber)) {
            throw new VehicleAlreadyExistsException(
                String.format("Vehicle with the registration number %s already" +
                                  " exists.", registrationNumber)
            );
        }
        vehicles.put(hashedRegistrationNumber, vehicle);
    }

    @Override
    public void update(VehiclePersistenceDto vehicle) {
        String registrationNumber = vehicle.getRegistrationNumber();
        if (findByRegistrationNumber(registrationNumber) == null) {
            throw new NonExistentVehicleException(
                String.format("Vehicle with the registration number %s don't" +
                                  " exists.", registrationNumber)
            );
        }
        vehicles.put(registrationNumber, vehicle);
    }

}
