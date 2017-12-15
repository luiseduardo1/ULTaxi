package ca.ulaval.glo4003.ultaxi.api.rate;

import ca.ulaval.glo4003.ultaxi.domain.rate.exception.InvalidRateException;
import ca.ulaval.glo4003.ultaxi.domain.rate.exception.NonExistentRateException;
import ca.ulaval.glo4003.ultaxi.domain.rate.exception.RateAlreadyExistsException;
import ca.ulaval.glo4003.ultaxi.domain.user.Role;
import ca.ulaval.glo4003.ultaxi.domain.vehicle.exception.InvalidVehicleTypeException;
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
    public Response createDistanceRate(DistanceRateDto distanceRateDto) throws InvalidRateException,
        RateAlreadyExistsException, InvalidVehicleTypeException {
        rateService.addDistanceRate(distanceRateDto);
        return Response.ok().build();
    }

    @Override
    public Response updateDistanceRate(DistanceRateDto distanceRateDto) throws InvalidRateException,
        NonExistentRateException, InvalidVehicleTypeException {
        rateService.updateDistanceRate(distanceRateDto);
        return Response.ok().build();
    }
}
