package ca.ulaval.glo4003.ultaxi.domain.rate;


import java.math.BigDecimal;

public abstract class Rate {

    protected BigDecimal rate;
    private RateType rateType;

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public RateType getRateType() {
        return rateType;
    }

    public void setRateType(RateType rateType) {
        this.rateType = rateType;
    }
}
