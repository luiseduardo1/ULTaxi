package ca.ulaval.glo4003.ultaxi.transfer.vehicle.exception;

import ca.ulaval.glo4003.ultaxi.domain.vehicle.exception.VehicleAlreadyExistsException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.logging.Logger;

@Provider
public class VehicleAlreadyExistsExceptionMapper implements ExceptionMapper<VehicleAlreadyExistsException> {

    private static final Logger logger = Logger.getLogger(VehicleAlreadyExistsException.class.getName());

    @Override
    public Response toResponse(VehicleAlreadyExistsException exception) {
        logger.info(exception.getMessage());
        return Response
            .status(Response.Status.BAD_REQUEST)
            .entity(exception.getMessage())
            .build();
    }
}
