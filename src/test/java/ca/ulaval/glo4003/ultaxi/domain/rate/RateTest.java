package ca.ulaval.glo4003.ultaxi.domain.rate;

import ca.ulaval.glo4003.ultaxi.domain.geolocation.Geolocation;
import ca.ulaval.glo4003.ultaxi.domain.money.Money;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;

import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class RateTest {

    private static final BigDecimal A_VALID_RATE = BigDecimal.TEN;
    private static final Geolocation A_VALID_STARTING_GEOLOCATION= new Geolocation(46.776635, -71.270671);
    private static final Geolocation A_VALID_ENDING_GEOLOCATION = new Geolocation(46.8083722, -71.2196447);

    Rate rate;

    @Before
    public void setUp() {
        rate = new DistanceRate();
        rate.setRate(A_VALID_RATE);
    }

    @Test
    public void givenValidGeolocalisation_whenCalculateTotalAmount_thenReturnValidTotalAmount(){

        Money totalAmount = rate.calculateTotalAmount(A_VALID_STARTING_GEOLOCATION, A_VALID_ENDING_GEOLOCATION);

        assertTrue(totalAmount.getValue().compareTo(BigDecimal.ZERO) > 0);
    }
}
