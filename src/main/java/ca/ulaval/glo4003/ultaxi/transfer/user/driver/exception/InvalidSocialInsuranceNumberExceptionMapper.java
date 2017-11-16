package ca.ulaval.glo4003.ultaxi.transfer.user.driver.exception;

import ca.ulaval.glo4003.ultaxi.domain.user.exception.InvalidSocialInsuranceNumberException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.logging.Logger;

@Provider
public class InvalidSocialInsuranceNumberExceptionMapper implements
    ExceptionMapper<InvalidSocialInsuranceNumberException> {

    private static final Logger logger = Logger.getLogger(InvalidSocialInsuranceNumberException.class.getName());

    @Override
    public Response toResponse(InvalidSocialInsuranceNumberException exception) {
        logger.info(exception.getMessage());
        return Response
            .status(Response.Status.BAD_REQUEST)
            .entity(exception.getMessage())
            .build();
    }
}
