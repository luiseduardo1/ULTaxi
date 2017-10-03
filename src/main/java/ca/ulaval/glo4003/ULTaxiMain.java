package ca.ulaval.glo4003;

import ca.ulaval.glo4003.ws.api.user.UserAuthenticationResourceImpl;
import ca.ulaval.glo4003.ws.api.user.UserResourceImpl;
import ca.ulaval.glo4003.ws.domain.user.TokenManager;
import ca.ulaval.glo4003.ws.domain.user.User;
import ca.ulaval.glo4003.ws.domain.user.UserAssembler;
import ca.ulaval.glo4003.ws.domain.user.UserRepository;
import ca.ulaval.glo4003.ws.domain.user.UserService;
import ca.ulaval.glo4003.ws.http.CORSResponseFilter;
import ca.ulaval.glo4003.ws.infrastructure.user.JWT.JWTTokenManager;
import ca.ulaval.glo4003.ws.infrastructure.user.TokenRepository;
import ca.ulaval.glo4003.ws.infrastructure.user.TokenRepositoryInMemory;
import ca.ulaval.glo4003.ws.infrastructure.user.UserDevDataFactory;
import ca.ulaval.glo4003.ws.infrastructure.user.UserRepositoryInMemory;
import ca.ulaval.glo4003.ws.util.AuthenticationFilter;
import ca.ulaval.glo4003.ws.util.AuthorizationFilter;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * RESTApi setup without using DI or spring
 */
@SuppressWarnings("all")
public class ULTaxiMain {

    public static boolean isDev = true; // Would be a JVM argument or in a .property file

    public static TokenManager tokenManager = new JWTTokenManager();

    public static UserRepository userRepository = new UserRepositoryInMemory();

    public static TokenRepository tokenRepository = new TokenRepositoryInMemory();

    public static void main(String[] args) throws Exception {

        // Setup API context (JERSEY + JETTY)
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/api/");
        ResourceConfig resourceConfig = ResourceConfig.forApplication(new Application() {
            @Override
            public Set<Object> getSingletons() {
                return createUserResource();
            }
        });

        ContainerRequestFilter authenticationFilter = new AuthenticationFilter(tokenManager);
        ContainerRequestFilter authaurizationFilter = new AuthorizationFilter(userRepository, tokenManager);
        resourceConfig.register(CORSResponseFilter.class);
        resourceConfig.register(authenticationFilter);
        resourceConfig.register(authaurizationFilter);


        ServletContainer servletContainer = new ServletContainer(resourceConfig);
        ServletHolder servletHolder = new ServletHolder(servletContainer);
        context.addServlet(servletHolder, "/*");

        // Setup http server
        ContextHandlerCollection contexts = new ContextHandlerCollection();
        contexts.setHandlers(new Handler[] {context});
        Server server = new Server(8080);
        server.setHandler(contexts);

        try {
            server.start();
            server.join();
        } finally {
            server.destroy();
        }
    }

    private static Set<Object> createUserResource() {

        Set<Object> resources = new HashSet<Object>();

        // For development ease
        if (isDev) {
            UserDevDataFactory userDevDataFactory = new UserDevDataFactory();
            List<User> users = userDevDataFactory.createMockData();
            //users.stream().forEach(userRepository::save);
        }

        UserAssembler userAssembler = new UserAssembler();
        UserService userService = new UserService(userRepository, userAssembler);

        resources.add(new UserResourceImpl(userService));
        resources.add(new UserAuthenticationResourceImpl(userService, tokenRepository, tokenManager));

        return resources;
    }

}
