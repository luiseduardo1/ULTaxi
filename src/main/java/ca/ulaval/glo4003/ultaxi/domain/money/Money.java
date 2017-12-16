package ca.ulaval.glo4003.ultaxi.domain.money;

import java.math.BigDecimal;

public class Money {

    private static final int NEGATIVE_AMOUNT = -1;

    public static final Money ZERO = new Money(BigDecimal.ZERO);

    private BigDecimal amount;

    public Money() {
    }

    public Money(double val) {
        this.amount = new BigDecimal(val);
    }

    public Money(BigDecimal val) {
        this.amount = val;
    }

    public Money add(BigDecimal addValue) {
        return new Money(this.amount.add(addValue));
    }

    public Money subtract(BigDecimal subtractValue) {
        return new Money(this.amount.subtract(subtractValue));
    }

    public BigDecimal getValue() {
        return this.amount;
    }

}
