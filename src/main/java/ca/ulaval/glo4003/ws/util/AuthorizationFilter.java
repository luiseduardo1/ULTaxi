package ca.ulaval.glo4003.ws.util;

import ca.ulaval.glo4003.ws.domain.user.*;

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
    @Context
    private ResourceInfo resourceInfo;
    private UserRepository userRepository;
    private TokenManager tokenManager;

    public AuthorizationFilter(UserRepository userRepository, TokenManager tokenManager) {
        this.userRepository = userRepository;
        this.tokenManager = tokenManager;
    }

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        String authorisationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
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
        } catch (InvalidUserNameException e) {
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
        User user = userRepository.findByName(username);
        if (user == null) {
            throw new InvalidUserNameException("Username is invalid.");
        }

        Role userRole = user.getRole();
        if (!allowedRoles.contains(userRole)) {
            throw new InvalidUserRoleException("Not a valid Permission.");
        }
    }
}
