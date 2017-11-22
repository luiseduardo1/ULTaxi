package ca.ulaval.glo4003.ultaxi.transfer.vehicle;

import java.math.BigDecimal;

public class VehiculeUpdateDistanceRateDTO {

    private String type;
    private String distanceUnit;
    private BigDecimal rate;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDistanceUnit() {
        return distanceUnit;
    }

    public void setDistanceUnit(String distanceUnit) {
        this.distanceUnit = distanceUnit;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }
}
