package ca.ulaval.glo4003.ultaxi.transfer.transportrequest.exception;

import ca.ulaval.glo4003.ultaxi.domain.transportrequest.exception.DriverHasNoTransportRequestAssignedException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.logging.Logger;

@Provider

public class DriverHasNoTransportRequestAssignedExceptionMapper implements ExceptionMapper<DriverHasNoTransportRequestAssignedException> {

    private static final Logger logger = Logger.getLogger(DriverHasNoTransportRequestAssignedException.class.getName());

    @Override
    public Response toResponse(DriverHasNoTransportRequestAssignedException exception) {
        logger.info(exception.getMessage());
        return Response
            .status(Response.Status.BAD_REQUEST)
            .entity(exception.getMessage())
            .build();
    }
}
