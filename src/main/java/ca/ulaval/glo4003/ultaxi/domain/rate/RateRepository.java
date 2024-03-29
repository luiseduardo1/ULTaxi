package ca.ulaval.glo4003.ultaxi.domain.rate;

import ca.ulaval.glo4003.ultaxi.domain.vehicle.VehicleType;
import ca.ulaval.glo4003.ultaxi.transfer.rate.RatePersistenceDto;

public interface RateRepository {

    void save(RatePersistenceDto ratePersistenceDto);

    void update(RatePersistenceDto ratePersistenceDto);

    RatePersistenceDto findDistanceRateByVehicleType(VehicleType vehicleType);
}
