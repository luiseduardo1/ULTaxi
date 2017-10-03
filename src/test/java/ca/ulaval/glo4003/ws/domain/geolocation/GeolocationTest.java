package ca.ulaval.glo4003.ws.domain.geolocation;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertFalse;

@RunWith(MockitoJUnitRunner.class)
public class GeolocationTest {

    private static final Double TOO_LOW_LATITUDE = -92.12332;
    private static final Double TOO_HIGH_LATITUDE = 92.12332;
    private static final Double TOO_LOW_LONGITUDE = -189.35465;
    private static final Double TOO_HIGH_LONGITUDE = 189.35465;
            
    private Geolocation geolocation;

    @Before
    public void setUp() throws Exception {
        geolocation = new Geolocation();
    }
    
    @Test
    public void givenALatitudeWithATooLowValue_whenValidateLatitude_thenReturnsFalse() {
        boolean result = geolocation.isLatitudeValid(TOO_LOW_LATITUDE);

        assertFalse(result);
    }

    @Test
    public void givenALatitudeWithATooHighValue_whenValidateLatitude_thenReturnsFalse() {
        boolean result = geolocation.isLatitudeValid(TOO_HIGH_LATITUDE);

        assertFalse(result);
    }
    
    @Test
    public void givenALongitudeWithATooLowValue_whenValidateLongitude_thenReturnsFalse() {
        boolean result = geolocation.isLongitudeValid(TOO_LOW_LONGITUDE);

        assertFalse(result);
    }

    @Test
    public void givenALongitudeWithATooHighValue_whenValidateLongitude_thenReturnsFalse() {
        boolean result = geolocation.isLongitudeValid(TOO_HIGH_LONGITUDE);

        assertFalse(result);
    }
}
