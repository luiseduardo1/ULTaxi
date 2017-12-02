package ca.ulaval.glo4003.ultaxi.transfer.rate;

import java.math.BigDecimal;

public class DistanceRateDto {
    private BigDecimal rate;
    private String vehicleType;

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }
}
