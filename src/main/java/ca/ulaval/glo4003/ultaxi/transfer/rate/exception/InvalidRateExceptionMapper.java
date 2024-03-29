package ca.ulaval.glo4003.ultaxi.transfer.rate.exception;

import ca.ulaval.glo4003.ultaxi.domain.rate.exception.InvalidRateException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.logging.Logger;

@Provider
public class InvalidRateExceptionMapper implements ExceptionMapper<InvalidRateException> {

    private static final Logger logger = Logger.getLogger(InvalidRateException.class.getName());

    @Override
    public Response toResponse(InvalidRateException exception) {
        logger.info(exception.getMessage());
        return Response
            .status(Response.Status.BAD_REQUEST)
            .entity(exception.getMessage())
            .build();
    }
}
