package ca.ulaval.glo4003.ultaxi.transfer.user.exception;

import ca.ulaval.glo4003.ultaxi.domain.user.exception.InvalidCredentialsException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.logging.Logger;

@Provider
public class InvalidCredentialsExceptionMapper implements ExceptionMapper<InvalidCredentialsException> {

    private static final Logger logger = Logger.getLogger(InvalidCredentialsException.class.getName());

    @Override
    public Response toResponse(InvalidCredentialsException exception) {
        logger.info(exception.getMessage());
        return Response
            .status(Response.Status.FORBIDDEN)
            .entity(exception.getMessage())
            .build();
    }
}
