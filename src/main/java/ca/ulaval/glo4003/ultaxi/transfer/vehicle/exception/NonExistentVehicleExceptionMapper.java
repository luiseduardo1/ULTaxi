package ca.ulaval.glo4003.ultaxi.transfer.vehicle.exception;

import ca.ulaval.glo4003.ultaxi.domain.vehicle.exception.NonExistentVehicleException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.logging.Logger;

@Provider
public class NonExistentVehicleExceptionMapper implements ExceptionMapper<NonExistentVehicleException> {

    private static final Logger logger = Logger.getLogger(NonExistentVehicleException.class.getName());

    @Override
    public Response toResponse(NonExistentVehicleException exception) {
        logger.info(exception.getMessage());
        return Response
            .status(Response.Status.BAD_REQUEST)
            .entity(exception.getMessage())
            .build();
    }
}
