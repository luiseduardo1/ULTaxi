package ca.ulaval.glo4003.ultaxi.transfer.transportrequest.exception;

import ca.ulaval.glo4003.ultaxi.domain.transportrequest.exception.NonExistentTransportRequestException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.logging.Logger;

@Provider
public class NonExistentTransportRequestExceptionMapper
    implements ExceptionMapper<NonExistentTransportRequestException> {

    private static final Logger logger = Logger.getLogger(NonExistentTransportRequestException.class.getName());

    @Override
    public Response toResponse(NonExistentTransportRequestException exception) {
        logger.info(exception.getMessage());
        return Response
            .status(Response.Status.BAD_REQUEST)
            .entity(exception.getMessage())
            .build();
    }
}
