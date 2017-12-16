package ca.ulaval.glo4003.ultaxi.infrastructure.rate;

import ca.ulaval.glo4003.ultaxi.domain.rate.RateRepository;
import ca.ulaval.glo4003.ultaxi.domain.rate.RateType;
import ca.ulaval.glo4003.ultaxi.domain.rate.exception.NonExistentRateException;
import ca.ulaval.glo4003.ultaxi.domain.rate.exception.RateAlreadyExistsException;
import ca.ulaval.glo4003.ultaxi.domain.vehicle.VehicleType;
import ca.ulaval.glo4003.ultaxi.transfer.rate.RatePersistenceDto;

import java.util.HashMap;
import java.util.Map;

public class RateRepositoryInMemory implements RateRepository {

    private final Map<RateType, Object> rates = new HashMap<>();
    private final Map<VehicleType, RatePersistenceDto> ratesByVehicleType =
        new HashMap<VehicleType, RatePersistenceDto>();

    public RateRepositoryInMemory() {
        rates.put(RateType.DISTANCE, ratesByVehicleType);
    }

    @Override
    public void save(RatePersistenceDto rate) {
        if (rate.getRateType().equals(RateType.DISTANCE)) {
            saveInDistance(rate);
        }
    }

    @Override
    public void update(RatePersistenceDto rate) {
        if (rate.getRateType().equals(RateType.DISTANCE)) {
            updateInDistance(rate);
        }
    }

    @Override
    public RatePersistenceDto findDistanceRateByVehicleType(VehicleType vehicleType) {
        return ratesByVehicleType.get(vehicleType);
    }


    private void saveInDistance(RatePersistenceDto rate) {
        if (ratesByVehicleType.containsKey(rate.getVehicleType())) {
            throw new RateAlreadyExistsException(
                String.format("Rate for vehicle type %s already exists.", rate.getVehicleType())
            );
        }
        ratesByVehicleType.put(rate.getVehicleType(), rate);
    }

    private void updateInDistance(RatePersistenceDto rate) {
        if (!ratesByVehicleType.containsKey(rate.getVehicleType())) {
            throw new NonExistentRateException(
                String.format("Rate with vehicle type %s don't exist", rate.getVehicleType())
            );
        }
        ratesByVehicleType.replace(rate.getVehicleType(), rate);
    }

}
