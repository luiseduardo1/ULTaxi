package ca.ulaval.glo4003.ultaxi.domain.geolocation;

import ca.ulaval.glo4003.ultaxi.domain.geolocation.exception.InvalidGeolocationException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class GeolocationTest {

    private static final Double A_VALID_LATITUDE = 32.12332;
    private static final Double A_VALID_LONGITUDE = 32.12332;
    private static final Double TOO_LOW_LATITUDE = -92.12332;
    private static final Double TOO_HIGH_LATITUDE = 92.12332;
    private static final Double TOO_LOW_LONGITUDE = -189.35465;
    private static final Double TOO_HIGH_LONGITUDE = 189.35465;

    @Test(expected = InvalidGeolocationException.class)
    public void givenALatitudeWithATooLowValue_whenSetLatitude_thenThrowsException() {
        new Geolocation(TOO_LOW_LATITUDE, A_VALID_LONGITUDE);
    }

    @Test(expected = InvalidGeolocationException.class)
    public void givenALatitudeWithATooLowValue_whenValidateLatitude_thenReturnsFalse() {
        new Geolocation(TOO_HIGH_LATITUDE, A_VALID_LONGITUDE);
    }

    @Test(expected = InvalidGeolocationException.class)
    public void givenALongitudeWithATooLowValue_whenValidateLongitude_thenReturnsFalse() {
        new Geolocation(A_VALID_LATITUDE, TOO_LOW_LONGITUDE);
    }

    @Test(expected = InvalidGeolocationException.class)
    public void givenALongitudeWithATooHighValue_whenValidateLongitude_thenReturnsFalse() {
        new Geolocation(A_VALID_LATITUDE, TOO_HIGH_LONGITUDE);
    }
}
