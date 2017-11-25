package ca.ulaval.glo4003.ultaxi.infrastructure.rate;

import ca.ulaval.glo4003.ultaxi.domain.rate.DistanceRate;
import ca.ulaval.glo4003.ultaxi.domain.rate.Rate;
import ca.ulaval.glo4003.ultaxi.domain.rate.RateRepository;
import ca.ulaval.glo4003.ultaxi.domain.rate.RateType;
import ca.ulaval.glo4003.ultaxi.domain.rate.exception.NonExistentRateException;
import ca.ulaval.glo4003.ultaxi.domain.rate.exception.RateAlreadyExistsException;
import ca.ulaval.glo4003.ultaxi.domain.vehicle.VehicleType;

import java.util.HashMap;
import java.util.Map;

public class RateRepositoryInMemory implements RateRepository {

    private final Map<RateType, Object> rates = new HashMap<>();
    private final Map<VehicleType, DistanceRate> ratesByVehicleType = new HashMap<>();

    public RateRepositoryInMemory() {
        rates.put(RateType.DISTANCE, ratesByVehicleType);
    }

    @Override
    public void save(Rate rate) {
        if(rate.getRateType().equals(RateType.DISTANCE)) {
            saveInDistance(rate);
        }
    }

    @Override
    public void update(Rate rate) {
        if(rate.getRateType().equals(RateType.DISTANCE)) {
            updateInDistance(rate);
        }
    }

    @Override
    public DistanceRate findDistanceRateByVehicleType(VehicleType vehicleType) {
        return ratesByVehicleType.get(vehicleType);
    }

    private void saveInDistance(Rate rate) {
        DistanceRate distanceRate = (DistanceRate) rate;
        if (ratesByVehicleType.containsKey(distanceRate.getVehicleType())) {
            throw new RateAlreadyExistsException(
                    String.format("Rate for vehicle type %s already exists.", distanceRate.getVehicleType())
            );
        }
        ratesByVehicleType.put(distanceRate.getVehicleType(), distanceRate);
    }

    private void updateInDistance(Rate rate) {
        DistanceRate distanceRate = (DistanceRate) rate;
        if (!ratesByVehicleType.containsKey(distanceRate.getVehicleType())) {
            throw new NonExistentRateException(
                    String.format("Rate with vehicle type %s don't exist", distanceRate.getVehicleType())
            );
        }
        ratesByVehicleType.replace(distanceRate.getVehicleType(), distanceRate);
    }

}
