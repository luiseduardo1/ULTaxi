package ca.ulaval.glo4003.ws.domain.geolocation;

import ca.ulaval.glo4003.ws.domain.geolocation.exception.InvalidGeolocationException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

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

    @Test(expected = InvalidGeolocationException.class)
    public void givenALatitudeWithATooLowValue_whenSetLatitude_thenThrowsException() {
        geolocation.setLatitude(TOO_LOW_LATITUDE);
    }

    @Test(expected = InvalidGeolocationException.class)
    public void givenALatitudeWithATooLowValue_whenValidateLatitude_thenReturnsFalse() {
        geolocation.setLatitude(TOO_HIGH_LATITUDE);
    }

    @Test(expected = InvalidGeolocationException.class)
    public void givenALongitudeWithATooLowValue_whenValidateLongitude_thenReturnsFalse() {
        geolocation.setLatitude(TOO_LOW_LONGITUDE);
    }

    @Test(expected = InvalidGeolocationException.class)
    public void givenALongitudeWithATooHighValue_whenValidateLongitude_thenReturnsFalse() {
        geolocation.setLatitude(TOO_HIGH_LONGITUDE);
    }
}
