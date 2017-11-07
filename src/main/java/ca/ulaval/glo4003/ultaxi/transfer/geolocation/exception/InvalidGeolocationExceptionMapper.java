package ca.ulaval.glo4003.ultaxi.transfer.geolocation.exception;

import ca.ulaval.glo4003.ultaxi.domain.geolocation.exception.InvalidGeolocationException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.logging.Level;
import java.util.logging.Logger;

@Provider
public class InvalidGeolocationExceptionMapper implements ExceptionMapper<InvalidGeolocationException> {

    private static final Logger logger = Logger.getLogger(InvalidGeolocationException.class.getName());

    @Override
    public Response toResponse(InvalidGeolocationException exception) {
        logger.log(Level.SEVERE, exception.getMessage());
        return Response
            .status(Response.Status.BAD_REQUEST)
            .entity(exception.getMessage())
            .build();
    }
}
