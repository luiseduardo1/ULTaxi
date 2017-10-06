package ca.ulaval.glo4003;

import ca.ulaval.glo4003.ws.api.user.UserAuthenticationResourceImpl;
import ca.ulaval.glo4003.ws.api.user.UserResourceImpl;
import ca.ulaval.glo4003.ws.domain.messaging.MessageQueue;
import ca.ulaval.glo4003.ws.domain.messaging.MessageQueueProducer;
import ca.ulaval.glo4003.ws.api.vehicle.VehicleResource;
import ca.ulaval.glo4003.ws.api.vehicle.VehicleResourceImpl;
import ca.ulaval.glo4003.ws.domain.user.TokenManager;
import ca.ulaval.glo4003.ws.domain.user.User;
import ca.ulaval.glo4003.ws.domain.user.UserAssembler;
import ca.ulaval.glo4003.ws.domain.user.UserAuthenticationService;
import ca.ulaval.glo4003.ws.domain.user.UserRepository;
import ca.ulaval.glo4003.ws.domain.user.UserService;
import ca.ulaval.glo4003.ws.domain.vehicle.Vehicle;
import ca.ulaval.glo4003.ws.domain.vehicle.VehicleAssembler;
import ca.ulaval.glo4003.ws.domain.vehicle.VehicleRepository;
import ca.ulaval.glo4003.ws.domain.vehicle.VehicleService;
import ca.ulaval.glo4003.ws.http.CORSResponseFilter;
import ca.ulaval.glo4003.ws.infrastructure.messaging.EmailSender;
import ca.ulaval.glo4003.ws.infrastructure.messaging.EmailSenderConfigurationPropertyFileReader;
import ca.ulaval.glo4003.ws.infrastructure.messaging.EmailSenderConfigurationReader;
import ca.ulaval.glo4003.ws.infrastructure.messaging.MessageQueueInMemory;
import ca.ulaval.glo4003.ws.infrastructure.user.JWT.JWTTokenManager;
import ca.ulaval.glo4003.ws.infrastructure.user.TokenRepository;
import ca.ulaval.glo4003.ws.infrastructure.user.TokenRepositoryInMemory;
import ca.ulaval.glo4003.ws.infrastructure.user.UserDevDataFactory;
import ca.ulaval.glo4003.ws.infrastructure.user.UserRepositoryInMemory;
import ca.ulaval.glo4003.ws.util.AuthenticationFilter;
import ca.ulaval.glo4003.ws.util.AuthorizationFilter;
import ca.ulaval.glo4003.ws.infrastructure.vehicle.VehicleDevDataFactory;
import ca.ulaval.glo4003.ws.infrastructure.vehicle.VehicleRepositoryInMemory;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * RESTApi setup without using DI or spring
 */
@SuppressWarnings("all")
public final class ULTaxiMain {

    private static final int SERVER_PORT = 8080;
    private static boolean isDev = true; // Would be a JVM argument or in a .property file
    private static MessageQueue messageQueueInMemory = new MessageQueueInMemory();
    private static String EMAIL_SENDER_CONFIGURATION_FILENAME = "emailSenderConfiguration.properties";

    public static TokenManager tokenManager = new JWTTokenManager();

    public static UserRepository userRepository = new UserRepositoryInMemory();

    public static TokenRepository tokenRepository = new TokenRepositoryInMemory();

    private ULTaxiMain() {
        throw new AssertionError("Instantiating main class...");
    }

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
        ContainerRequestFilter authorizationFilter = new AuthorizationFilter(userRepository, tokenManager);
        resourceConfig.register(CORSResponseFilter.class);
        resourceConfig.register(authenticationFilter);
        resourceConfig.register(authorizationFilter);

        ServletContainer servletContainer = new ServletContainer(resourceConfig);
        ServletHolder servletHolder = new ServletHolder(servletContainer);
        context.addServlet(servletHolder, "/*");

        // Setup messaging thread
        EmailSenderConfigurationReader emailSenderConfigurationReader = new
            EmailSenderConfigurationPropertyFileReader(EMAIL_SENDER_CONFIGURATION_FILENAME);
        EmailSender emailSender = new EmailSender(emailSenderConfigurationReader);
        Thread messagingThread = new Thread(new MessagingThread(messageQueueInMemory, emailSender));
        messagingThread.start();

        // Setup http server
        ContextHandlerCollection contexts = new ContextHandlerCollection();
        contexts.setHandlers(new Handler[] {context});
        Server server = new Server(SERVER_PORT);
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

        UserAuthenticationService userAuthenticationService =
            new UserAuthenticationService(userRepository);
        UserAssembler userAssembler = new UserAssembler();
        MessageQueueProducer messageQueueProducer = new MessageQueueProducer(messageQueueInMemory);
        UserService userService = new UserService(userRepository, userAssembler, userAuthenticationService, messageQueueProducer);

        resources.add(new UserResourceImpl(userService));
        resources.add(new UserAuthenticationResourceImpl(userService, tokenRepository, tokenManager));

        return resources;
    }
    
    private static VehicleResource createVehicleResource() {
        // Setup resources' dependencies (DOMAIN + INFRASTRUCTURE)
        VehicleRepository vehicleRepository = new VehicleRepositoryInMemory();

        // For development ease
        if (isDev) {
            VehicleDevDataFactory vehicleDevDataFactory = new VehicleDevDataFactory();
            List<Vehicle> vehicles = vehicleDevDataFactory.createMockData();
            //vehicles.stream().forEach(vehicleRepository::save);
        }

        VehicleAssembler vehicleAssembler = new VehicleAssembler();
        VehicleService vehicleService = new VehicleService(vehicleRepository, vehicleAssembler);

        return new VehicleResourceImpl(vehicleService);
    }
}
