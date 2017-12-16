package ca.ulaval.glo4003.ultaxi.utils.DistanceCalculator;

import ca.ulaval.glo4003.ultaxi.domain.geolocation.Geolocation;
import ca.ulaval.glo4003.ultaxi.utils.distancecalculator.HaversineDistance;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertTrue;

public class HaversineDistanceTest {

    private HaversineDistance haversineDistance;

    private static final Geolocation A_VALID_STARTING_GEOLOCATION= new Geolocation(46.776635, -71.270671);
    private static final Geolocation A_VALID_ENDING_GEOLOCATION = new Geolocation(46.8083722, -71.2196447);
    private static final double A_INVALID_DISTANCE = 0;

    @Before
    public void setUp(){
        haversineDistance = new HaversineDistance();
    }

    @Test
    public void givenANullValue_whenHashing_thenReturnsNull() {
        Double distance = haversineDistance.calculDistance(A_VALID_STARTING_GEOLOCATION.getLatitude(),
                A_VALID_STARTING_GEOLOCATION.getLongitude(), A_VALID_ENDING_GEOLOCATION.getLatitude(),
                A_VALID_ENDING_GEOLOCATION.getLongitude());

        assertTrue(A_INVALID_DISTANCE < distance);
    }
}