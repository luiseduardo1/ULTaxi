package ca.ulaval.glo4003.ultaxi.transfer.transportrequest.exception;

import ca.ulaval.glo4003.ultaxi.domain.transportrequest.exception.InvalidTransportRequestCompletionException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.logging.Logger;

@Provider
public class InvalidTransportRequestCompletionExceptionMapper
    implements ExceptionMapper<InvalidTransportRequestCompletionException> {

    private static final Logger logger = Logger.getLogger(InvalidTransportRequestCompletionException.class.getName());

    @Override
    public Response toResponse(InvalidTransportRequestCompletionException exception) {
        logger.info(exception.getMessage());
        return Response
            .status(Response.Status.BAD_REQUEST)
            .entity(exception.getMessage())
            .build();
    }
}
