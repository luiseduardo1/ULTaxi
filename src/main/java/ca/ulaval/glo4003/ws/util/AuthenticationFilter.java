package ca.ulaval.glo4003.ws.util;

import ca.ulaval.glo4003.ws.domain.user.TokenManager;
import ca.ulaval.glo4003.ws.infrastructure.user.jwt.InvalidTokenException;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

@Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {

    private static final String AUTHENTICATION_SCHEME = "Bearer";

    private TokenManager tokenManager;

    public AuthenticationFilter(TokenManager tokenManager) {
        this.tokenManager = tokenManager;
    }


    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        String authorisationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        if (!isTokenBasedAuthentication(authorisationHeader)) {
            abortUnauthorized(requestContext);
            return;
        }

        String token = authorisationHeader.substring(AUTHENTICATION_SCHEME.length()).trim();

        try {
            tokenManager.validateTokenExpirationDate(token);
        } catch (InvalidTokenException e) {
            abortUnauthorized(requestContext);
        }

    }

    private boolean isTokenBasedAuthentication(String authorizationHeader) {
        return authorizationHeader != null && authorizationHeader.toLowerCase()
            .startsWith(AUTHENTICATION_SCHEME.toLowerCase() + " ");
    }

    private void abortUnauthorized(ContainerRequestContext requestContext) {
        requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                                     .header(HttpHeaders.WWW_AUTHENTICATE, AUTHENTICATION_SCHEME).build());
    }

}
