package ca.ulaval.glo4003.ultaxi.domain.vehicle;

import java.math.BigDecimal;

public class Van extends Vehicle {

    private static final VehicleType VEHICLE_TYPE = VehicleType.VAN;
    private static BigDecimal distanceRate = BigDecimal.ONE;

    public Van(String color, String model, String registrationNumber) {
        super(color, model, registrationNumber);
    }

    @Override
    public VehicleType getType() {
        return VEHICLE_TYPE;
    }

    @Override
    public BigDecimal getDistanceRate() {
        return distanceRate;
    }

    @Override
    public void setDistanceRate(BigDecimal distanceRate) {
        this.distanceRate = distanceRate;
    }
}
