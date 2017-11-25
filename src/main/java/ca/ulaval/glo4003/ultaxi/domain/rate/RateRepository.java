package ca.ulaval.glo4003.ultaxi.domain.rate;

import ca.ulaval.glo4003.ultaxi.domain.vehicle.VehicleType;

public interface RateRepository {

    void save(Rate rate);

    void update(Rate rate);

    DistanceRate findDistanceRateByVehicleType(VehicleType vehicleType);
}
