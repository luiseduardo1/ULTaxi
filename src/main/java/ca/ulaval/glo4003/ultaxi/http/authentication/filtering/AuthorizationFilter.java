package ca.ulaval.glo4003.ultaxi.http.authentication.filtering;

import ca.ulaval.glo4003.ultaxi.domain.user.Role;
import ca.ulaval.glo4003.ultaxi.domain.user.TokenManager;
import ca.ulaval.glo4003.ultaxi.domain.user.TokenRepository;
import ca.ulaval.glo4003.ultaxi.domain.user.User;
import ca.ulaval.glo4003.ultaxi.domain.user.UserRepository;
import ca.ulaval.glo4003.ultaxi.domain.user.exception.InvalidUserRoleException;
import ca.ulaval.glo4003.ultaxi.domain.user.exception.InvalidUsernameException;
import ca.ulaval.glo4003.ultaxi.infrastructure.user.jwt.exception.InexistantTokenException;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthorizationFilter implements ContainerRequestFilter {

    private static final String AUTHENTICATION_SCHEME = "Bearer";
    private final UserRepository userRepository;
    private final TokenManager tokenManager;
    private final TokenRepository tokenRepository;
    @Context
    private ResourceInfo resourceInfo;

    public AuthorizationFilter(UserRepository userRepository, TokenManager tokenManager,
                               TokenRepository tokenRepository) {
        this.userRepository = userRepository;
        this.tokenManager = tokenManager;
        this.tokenRepository = tokenRepository;
    }

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        String authorisationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        if (authorisationHeader == null) {
            return;
        }

        String token = authorisationHeader.substring(AUTHENTICATION_SCHEME.length()).trim();

        Class<?> resourceClass = resourceInfo.getResourceClass();
        List<Role> classRoles = extractRoles(resourceClass);

        Method resourceMethod = resourceInfo.getResourceMethod();
        List<Role> methodRoles = extractRoles(resourceMethod);

        try {
            if (methodRoles.isEmpty()) {
                checkPermissions(classRoles, token);
            } else {
                checkPermissions(methodRoles, token);
            }
        } catch (InvalidUsernameException | InvalidUserRoleException e) {
            requestContext.abortWith(Response.status(Response.Status.FORBIDDEN).build());
        }

    }

    private List<Role> extractRoles(AnnotatedElement annotatedElement) {
        if (annotatedElement == null) {
            return new ArrayList<>();
        } else {
            Secured secured = annotatedElement.getAnnotation(Secured.class);
            if (secured == null) {
                return new ArrayList<>();
            } else {
                Role[] allowedRoles = secured.value();
                return Arrays.asList(allowedRoles);
            }
        }
    }

    private void checkPermissions(List<Role> allowedRoles, String token) {
        String username = tokenManager.getUsername(token);
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new InvalidUsernameException("Username is invalid.");
        }

        Role userRole = user.getRole();
        if (!allowedRoles.contains(userRole)) {
            throw new InvalidUserRoleException("Not a valid Permission.");
        }

        if (checkTokenExistence(token)) {
            throw new InexistantTokenException("Not an existent token.");
        }
    }

    private boolean checkTokenExistence(String token) {
        return tokenRepository.getToken(tokenManager.getTokenId(token)) == null;
    }


}
