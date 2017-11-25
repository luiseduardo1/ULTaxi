package ca.ulaval.glo4003.ultaxi.domain.rate;

import ca.ulaval.glo4003.ultaxi.domain.vehicle.VehicleType;
import ca.ulaval.glo4003.ultaxi.domain.vehicle.exception.InvalidVehicleTypeException;

import java.math.BigDecimal;

public class DistanceRate extends Rate {
    private VehicleType vehicleType;

    public DistanceRate() {
        rate = BigDecimal.ONE;
        vehicleType = VehicleType.CAR;
    }

    public void setVehicleType(String vehicleType) {
        try {
            this.vehicleType = VehicleType.valueOf(vehicleType.toUpperCase().trim());
        } catch (NullPointerException | IllegalArgumentException | IndexOutOfBoundsException exception) {
            throw new InvalidVehicleTypeException(String.format("%s is not a valid vehicle type.", vehicleType));
        }
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }
}
