package ca.ulaval.glo4003.ultaxi.domain.vehicle;

import ca.ulaval.glo4003.ultaxi.transfer.vehicle.VehiclePersistenceDto;

public interface VehicleRepository {

    VehiclePersistenceDto findByRegistrationNumber(String registrationNumber);

    void save(VehiclePersistenceDto vehicle);

    void update(VehiclePersistenceDto vehicle);
}
