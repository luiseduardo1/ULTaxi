package ca.ulaval.glo4003;

import ca.ulaval.glo4003.ultaxi.api.middleware.CORSResponseFilter;
import ca.ulaval.glo4003.ultaxi.api.middleware.authentication.AuthenticationFilter;
import ca.ulaval.glo4003.ultaxi.api.middleware.authentication.AuthorizationFilter;
import ca.ulaval.glo4003.ultaxi.api.transportrequest.TransportRequestResource;
import ca.ulaval.glo4003.ultaxi.api.transportrequest.TransportRequestResourceImpl;
import ca.ulaval.glo4003.ultaxi.api.user.UserAuthenticationResource;
import ca.ulaval.glo4003.ultaxi.api.user.UserAuthenticationResourceImpl;
import ca.ulaval.glo4003.ultaxi.api.user.UserResource;
import ca.ulaval.glo4003.ultaxi.api.user.UserResourceImpl;
import ca.ulaval.glo4003.ultaxi.api.user.driver.DriverResource;
import ca.ulaval.glo4003.ultaxi.api.user.driver.DriverResourceImpl;
import ca.ulaval.glo4003.ultaxi.api.vehicle.VehicleResource;
import ca.ulaval.glo4003.ultaxi.api.vehicle.VehicleResourceImpl;
import ca.ulaval.glo4003.ultaxi.domain.messaging.MessageQueue;
import ca.ulaval.glo4003.ultaxi.domain.messaging.MessageQueueProducer;
import ca.ulaval.glo4003.ultaxi.domain.transportrequest.TransportRequestRepository;
import ca.ulaval.glo4003.ultaxi.domain.user.TokenManager;
import ca.ulaval.glo4003.ultaxi.domain.user.TokenRepository;
import ca.ulaval.glo4003.ultaxi.domain.user.User;
import ca.ulaval.glo4003.ultaxi.domain.user.UserRepository;
import ca.ulaval.glo4003.ultaxi.domain.vehicle.Vehicle;
import ca.ulaval.glo4003.ultaxi.domain.vehicle.VehicleRepository;
import ca.ulaval.glo4003.ultaxi.infrastructure.messaging.EmailSender;
import ca.ulaval.glo4003.ultaxi.infrastructure.messaging.EmailSenderConfigurationPropertyFileReader;
import ca.ulaval.glo4003.ultaxi.infrastructure.messaging.EmailSenderConfigurationReader;
import ca.ulaval.glo4003.ultaxi.infrastructure.messaging.MessageQueueInMemory;
import ca.ulaval.glo4003.ultaxi.infrastructure.transportrequest.TransportRequestRepositoryInMemory;
import ca.ulaval.glo4003.ultaxi.infrastructure.user.TokenRepositoryInMemory;
import ca.ulaval.glo4003.ultaxi.infrastructure.user.UserDevDataFactory;
import ca.ulaval.glo4003.ultaxi.infrastructure.user.UserRepositoryInMemory;
import ca.ulaval.glo4003.ultaxi.infrastructure.user.jwt.JWTTokenManager;
import ca.ulaval.glo4003.ultaxi.infrastructure.vehicle.VehicleDevDataFactory;
import ca.ulaval.glo4003.ultaxi.infrastructure.vehicle.VehicleRepositoryInMemory;
import ca.ulaval.glo4003.ultaxi.service.transportrequest.TransportRequestService;
import ca.ulaval.glo4003.ultaxi.service.user.UserAuthenticationService;
import ca.ulaval.glo4003.ultaxi.service.user.UserService;
import ca.ulaval.glo4003.ultaxi.service.user.driver.DriverService;
import ca.ulaval.glo4003.ultaxi.service.vehicle.VehicleService;
import ca.ulaval.glo4003.ultaxi.transfer.transportrequest.TransportRequestAssembler;
import ca.ulaval.glo4003.ultaxi.transfer.user.UserAssembler;
import ca.ulaval.glo4003.ultaxi.transfer.user.driver.DriverAssembler;
import ca.ulaval.glo4003.ultaxi.transfer.vehicle.VehicleAssembler;
import ca.ulaval.glo4003.ultaxi.utils.hashing.BcryptHashing;
import ca.ulaval.glo4003.ultaxi.utils.hashing.HashingStrategy;
import jersey.repackaged.com.google.common.collect.Sets;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Application;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * RESTApi setup without using DI or spring
 */
@SuppressWarnings("all")
public final class ULTaxiMain {

    private static final int SERVER_PORT = 8080;
    public static final TokenManager tokenManager = new JWTTokenManager();
    public static final TokenRepository tokenRepository = new TokenRepositoryInMemory();
    public static final UserRepository userRepository = new UserRepositoryInMemory();
    public static final VehicleRepository vehicleRepository = new VehicleRepositoryInMemory();
    private static boolean isDev = true; // Would be a JVM argument or in a .property file
    private static String EMAIL_SENDER_CONFIGURATION_FILENAME = "emailSenderConfiguration.properties";
    private static MessageQueue messageQueue = new MessageQueueInMemory();

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
                return getContextResources();
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
        EmailSenderConfigurationReader emailSenderConfigurationReader =
            new EmailSenderConfigurationPropertyFileReader(EMAIL_SENDER_CONFIGURATION_FILENAME);
        EmailSender emailSender = new EmailSender(emailSenderConfigurationReader);
        Thread messagingThread = new Thread(new MessagingThread(messageQueue, emailSender));
        messagingThread.start();

        // Setup http server
        ContextHandlerCollection contexts = new ContextHandlerCollection();
        contexts.setHandlers(new Handler[]{context});
        Server server = new Server(SERVER_PORT);
        server.setHandler(contexts);

        try {
            server.start();
            server.join();
        } finally {
            server.destroy();
        }
    }

    private static Set<Object> getContextResources() {
        UserService userService = createUserService();
        UserAuthenticationService userAuthenticationService = createUserAuthenticationService();
        VehicleService vehicleService = createVehicleService();
        DriverService driverService = createDriverService();

        UserResource userResource = createUserResource(userService);
        DriverResource driverResource = createDriverResource(driverService);
        VehicleResource vehicleResource = createVehicleResource(vehicleService);
        UserAuthenticationResource userAuthenticationResource = createUseAuthenticationResource
            (userAuthenticationService);
        TransportRequestResource transportRequestResource = createTransportRequestResource();

        return Collections.unmodifiableSet(Sets.newHashSet(driverResource,
                                                           userResource,
                                                           vehicleResource,
                                                           userAuthenticationResource,
                                                           transportRequestResource));
    }

    private static UserService createUserService() {
        MessageQueueProducer messageQueueProducer = new MessageQueueProducer(messageQueue);
        UserService userService = new UserService(userRepository, createUserAssembler(), messageQueueProducer);
        return userService;
    }

    private static UserAuthenticationService createUserAuthenticationService() {
        UserAuthenticationService userAuthenticationService = new UserAuthenticationService(userRepository,
                                                                                            createUserAssembler());

        return userAuthenticationService;
    }

    private static UserAssembler createUserAssembler() {
        return new UserAssembler(createHashingStrategy());
    }

    private static DriverAssembler createDriverAssembler() {
        return new DriverAssembler(createHashingStrategy());
    }

    private static DriverResource createDriverResource(DriverService driverService) {
        return new DriverResourceImpl(driverService);
    }

    private static HashingStrategy createHashingStrategy() {
        return new BcryptHashing();
    }

    private static VehicleService createVehicleService() {
        VehicleAssembler vehicleAssembler = new VehicleAssembler();
        VehicleService vehicleService = new VehicleService(vehicleRepository, vehicleAssembler);

        return vehicleService;
    }

    private static UserResource createUserResource(UserService userService) {
        if (isDev) {
            UserDevDataFactory userDevDataFactory = new UserDevDataFactory();
            List<User> users = userDevDataFactory.createMockData();
            //users.stream().forEach(userRepository::save);
        }

        return new UserResourceImpl(userService);
    }

    private static VehicleResource createVehicleResource(VehicleService vehicleService) {
        if (isDev) {
            VehicleDevDataFactory vehicleDevDataFactory = new VehicleDevDataFactory();
            List<Vehicle> vehicles = vehicleDevDataFactory.createMockData();
            //vehicles.stream().forEach(vehicleRepository::save);
        }

        return new VehicleResourceImpl(vehicleService);
    }

    private static UserAuthenticationResource createUseAuthenticationResource(UserAuthenticationService
        userAuthenticationService) {
        return new UserAuthenticationResourceImpl(userAuthenticationService, tokenRepository, tokenManager);
    }

    private static TransportRequestResource createTransportRequestResource() {
        TransportRequestRepository transportRequestRepository = new TransportRequestRepositoryInMemory();
        TransportRequestAssembler transportRequestAssembler = new TransportRequestAssembler();
        TransportRequestService transportRequestService = new TransportRequestService(transportRequestRepository,
                                                                                      transportRequestAssembler);

        return new TransportRequestResourceImpl(transportRequestService);
    }

    private static DriverService createDriverService() {
        DriverAssembler driverAssembler = createDriverAssembler();
        return new DriverService(userRepository, driverAssembler);
    }
}
