package ca.ulaval.glo4003.ultaxi.infrastructure.vehicle;

import ca.ulaval.glo4003.ultaxi.domain.vehicle.Vehicle;
import ca.ulaval.glo4003.ultaxi.domain.vehicle.VehicleRepository;
import ca.ulaval.glo4003.ultaxi.domain.vehicle.exception.VehicleAlreadyExistsException;

import java.util.HashMap;
import java.util.Map;

public class VehicleRepositoryInMemory implements VehicleRepository {

    private Map<String, Vehicle> vehicles = new HashMap<>();

    @Override
    public Vehicle findByRegistrationNumber(String registrationNumber) {
        String formattedRegistrationNumber = registrationNumber.toUpperCase().trim();
        return vehicles.get(formattedRegistrationNumber);
    }

    @Override
    public void save(Vehicle vehicle) {
        String registrationNumber = vehicle.getRegistrationNumber().toUpperCase().trim();
        if (vehicles.containsKey(registrationNumber)) {
            throw new VehicleAlreadyExistsException(
                    String.format("Vehicle with the registration number %s already" +
                            " exists.", registrationNumber)
            );
        }
        vehicles.put(registrationNumber, vehicle);
    }
}
