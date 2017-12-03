package ca.ulaval.glo4003.ultaxi.api.rate;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;

import ca.ulaval.glo4003.ultaxi.service.rate.RateService;
import ca.ulaval.glo4003.ultaxi.transfer.rate.DistanceRateDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ws.rs.core.Response;

@RunWith(MockitoJUnitRunner.class)
public class RateResourceImplTest {

    @Mock
    private RateService rateService;
    @Mock
    private DistanceRateDto distanceRateDto;

    private RateResource rateResource;

    @Before
    public void setUp() {
        rateResource = new RateResourceImpl(rateService);
    }

    @Test
    public void givenValidDistanceRate_whenCreateDistanceRate_thenReturnsOk() {
        Response response = rateResource.createDistanceRate(distanceRateDto);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void whenCreateDistanceRate_thenDelegateToRateService() {
        rateResource.createDistanceRate(distanceRateDto);
        verify(rateService).addDistanceRate(distanceRateDto);
    }

    @Test
    public void givenValidDistanceRate_whenUpdateDistanceRate_thenReturnsOK() {
        rateResource.createDistanceRate(distanceRateDto);
        Response response = rateResource.updateDistanceRate(distanceRateDto);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void whenUpdatingDistanceRate_thenDelegateToRateService() {
        rateResource.createDistanceRate(distanceRateDto);
        rateResource.updateDistanceRate(distanceRateDto);

        verify(rateService).updateDistanceRate(distanceRateDto);
    }
}