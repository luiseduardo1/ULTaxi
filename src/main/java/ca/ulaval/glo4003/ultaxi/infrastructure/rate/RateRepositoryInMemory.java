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
    public void save(RatePersistenceDto ratePersistenceDto) {
        if (ratePersistenceDto.getRateType().equals(RateType.DISTANCE)) {
            saveInDistance(ratePersistenceDto);
        }
    }

    @Override
    public void update(RatePersistenceDto ratePersistenceDto) {
        if (ratePersistenceDto.getRateType().equals(RateType.DISTANCE)) {
            updateInDistance(ratePersistenceDto);
        }
    }

    @Override
    public RatePersistenceDto findDistanceRateByVehicleType(VehicleType vehicleType) {
        return ratesByVehicleType.get(vehicleType);
    }


    private void saveInDistance(RatePersistenceDto ratePersistenceDto) {
        if (ratesByVehicleType.containsKey(ratePersistenceDto.getVehicleType())) {
            throw new RateAlreadyExistsException(
                String.format("Rate for vehicle type %s already exists.", ratePersistenceDto.getVehicleType())
            );
        }
        ratesByVehicleType.put(ratePersistenceDto.getVehicleType(), ratePersistenceDto);
    }

    private void updateInDistance(RatePersistenceDto ratePersistenceDto) {
        if (!ratesByVehicleType.containsKey(ratePersistenceDto.getVehicleType())) {
            throw new NonExistentRateException(
                String.format("Rate with vehicle type %s don't exist", ratePersistenceDto.getVehicleType())
            );
        }
        ratesByVehicleType.replace(ratePersistenceDto.getVehicleType(), ratePersistenceDto);
    }

}
