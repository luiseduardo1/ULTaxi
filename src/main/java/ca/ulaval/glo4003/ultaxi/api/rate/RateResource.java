package ca.ulaval.glo4003.ultaxi.api.rate;

import ca.ulaval.glo4003.ultaxi.transfer.rate.DistanceRateDto;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/rates")
public interface RateResource {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    Response updateDistanceRate(DistanceRateDto distanceRateDto);
}
