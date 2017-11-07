package ca.ulaval.glo4003.ultaxi.transfer.user.exception;

import ca.ulaval.glo4003.ultaxi.infrastructure.user.jwt.exception.InvalidTokenException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.logging.Level;
import java.util.logging.Logger;

@Provider
public class InvalidTokenExceptionMapper implements ExceptionMapper<InvalidTokenException> {

    private static final Logger logger = Logger.getLogger(InvalidTokenException.class.getName());

    @Override
    public Response toResponse(InvalidTokenException exception) {
        logger.log(Level.SEVERE, exception.getMessage());
        return Response
            .status(Response.Status.BAD_REQUEST)
            .entity(exception.getMessage())
            .build();
    }
}
