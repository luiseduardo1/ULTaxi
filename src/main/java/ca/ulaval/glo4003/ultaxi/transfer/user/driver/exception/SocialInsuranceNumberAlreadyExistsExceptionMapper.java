package ca.ulaval.glo4003.ultaxi.transfer.user.driver.exception;

import ca.ulaval.glo4003.ultaxi.domain.user.exception.SocialInsuranceNumberAlreadyExistException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.logging.Logger;

@Provider
public class SocialInsuranceNumberAlreadyExistsExceptionMapper implements ExceptionMapper<SocialInsuranceNumberAlreadyExistException> {

    private static final Logger logger = Logger.getLogger(SocialInsuranceNumberAlreadyExistException.class.getName());

    @Override
    public Response toResponse(SocialInsuranceNumberAlreadyExistException exception) {
        logger.info(exception.getMessage());
        return Response
            .status(Response.Status.BAD_REQUEST)
            .entity(exception.getMessage())
            .build();
    }
}
