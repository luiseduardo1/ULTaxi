package ca.ulaval.glo4003.ultaxi.transfer.user.driver.exception;

import ca.ulaval.glo4003.ultaxi.domain.user.exception.EmptySearchResultsException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.logging.Logger;

@Provider
public class EmptySearchResultsExceptionMapper implements ExceptionMapper<EmptySearchResultsException> {

    private static final Logger logger = Logger.getLogger(EmptySearchResultsException.class.getName());

    @Override
    public Response toResponse(EmptySearchResultsException exception) {
        logger.info(exception.getMessage());
        return Response
            .status(Response.Status.NOT_FOUND)
            .entity(exception.getMessage())
            .build();
    }
}
