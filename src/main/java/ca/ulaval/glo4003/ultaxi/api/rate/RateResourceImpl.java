package ca.ulaval.glo4003.ultaxi.api.rate;

import ca.ulaval.glo4003.ultaxi.domain.user.Role;
import ca.ulaval.glo4003.ultaxi.http.authentication.filtering.Secured;
import ca.ulaval.glo4003.ultaxi.service.rate.RateService;
import ca.ulaval.glo4003.ultaxi.transfer.rate.DistanceRateDto;

import javax.ws.rs.core.Response;

@Secured(Role.ADMINISTRATOR)
public class RateResourceImpl implements RateResource {

    private final RateService rateService;

    public RateResourceImpl(RateService rateService) {
        this.rateService = rateService;
    }

    @Override
    public Response createDistanceRate(DistanceRateDto distanceRateDto) {
        rateService.addDistanceRate(distanceRateDto);
        return Response.ok().build();
    }

    @Override
    public Response updateDistanceRate(DistanceRateDto distanceRateDto) {
        rateService.updateDistanceRate(distanceRateDto);
        return Response.ok().build();
    }
}
