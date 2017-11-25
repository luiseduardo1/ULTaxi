package ca.ulaval.glo4003.ultaxi.transfer.user.exception;

import ca.ulaval.glo4003.ultaxi.domain.user.exception.InvalidUsernameException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.logging.Logger;

@Provider
public class InvalidUsernameExceptionMapper implements ExceptionMapper<InvalidUsernameException> {

    private static final Logger logger = Logger.getLogger(InvalidUsernameException.class.getName());

    @Override
    public Response toResponse(InvalidUsernameException exception) {
        logger.info(exception.getMessage());
        return Response
            .status(Response.Status.BAD_REQUEST)
            .entity(exception.getMessage())
            .build();
    }
}
