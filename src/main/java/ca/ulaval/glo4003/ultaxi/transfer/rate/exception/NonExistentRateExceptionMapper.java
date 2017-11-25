package ca.ulaval.glo4003.ultaxi.transfer.rate.exception;

import ca.ulaval.glo4003.ultaxi.domain.rate.exception.NonExistentRateException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.logging.Logger;

@Provider
public class NonExistentRateExceptionMapper implements ExceptionMapper<NonExistentRateException> {

    private static final Logger logger = Logger.getLogger(NonExistentRateException.class.getName());

    @Override
    public Response toResponse(NonExistentRateException exception) {
        return Response
                .status(Response.Status.BAD_REQUEST)
                .entity(exception.getMessage())
                .build();
    }
}
