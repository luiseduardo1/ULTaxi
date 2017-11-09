package ca.ulaval.glo4003.ultaxi.transfer.vehicle.exception;

import ca.ulaval.glo4003.ultaxi.domain.vehicle.exception.InvalidVehicleDissociationException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.logging.Logger;

@Provider
public class InvalidVehicleDissociationExceptionMapper implements ExceptionMapper<InvalidVehicleDissociationException> {

    private static final Logger logger = Logger.getLogger(InvalidVehicleDissociationException.class.getName());

    @Override
    public Response toResponse(InvalidVehicleDissociationException exception) {
        logger.info(exception.getMessage());
        return Response
            .status(Response.Status.BAD_REQUEST)
            .entity(exception.getMessage())
            .build();
    }
}
