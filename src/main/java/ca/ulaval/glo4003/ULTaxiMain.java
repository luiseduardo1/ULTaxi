package ca.ulaval.glo4003;

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
import ca.ulaval.glo4003.ultaxi.domain.messaging.MessagingTaskQueue;
import ca.ulaval.glo4003.ultaxi.domain.messaging.MessagingTaskProducer;
import ca.ulaval.glo4003.ultaxi.domain.transportrequest.TransportRequestRepository;
import ca.ulaval.glo4003.ultaxi.domain.user.TokenManager;
import ca.ulaval.glo4003.ultaxi.domain.user.TokenRepository;
import ca.ulaval.glo4003.ultaxi.domain.user.User;
import ca.ulaval.glo4003.ultaxi.domain.user.UserRepository;
import ca.ulaval.glo4003.ultaxi.domain.vehicle.Vehicle;
import ca.ulaval.glo4003.ultaxi.domain.vehicle.VehicleRepository;
import ca.ulaval.glo4003.ultaxi.http.CORSResponseFilter;
import ca.ulaval.glo4003.ultaxi.http.authentication.filtering.AuthenticationFilter;
import ca.ulaval.glo4003.ultaxi.http.authentication.filtering.AuthorizationFilter;
import ca.ulaval.glo4003.ultaxi.infrastructure.messaging.email.EmailSender;
import ca.ulaval.glo4003.ultaxi.infrastructure.messaging.email.EmailSenderConfigurationPropertyFileReader;
import ca.ulaval.glo4003.ultaxi.infrastructure.messaging.email.EmailSenderConfigurationReader;
import ca.ulaval.glo4003.ultaxi.infrastructure.messaging.MessagingTaskQueueInMemory;
import ca.ulaval.glo4003.ultaxi.infrastructure.messaging.MessagingTaskConsumerImpl;
import ca.ulaval.glo4003.ultaxi.infrastructure.messaging.MessagingTaskProducerImpl;
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
import ca.ulaval.glo4003.ultaxi.service.user.driver.DriverValidator;
import ca.ulaval.glo4003.ultaxi.service.vehicle.VehicleService;
import ca.ulaval.glo4003.ultaxi.transfer.transportrequest.TransportRequestAssembler;
import ca.ulaval.glo4003.ultaxi.transfer.user.UserAssembler;
import ca.ulaval.glo4003.ultaxi.transfer.user.driver.DriverAssembler;
import ca.ulaval.glo4003.ultaxi.transfer.vehicle.VehicleAssembler;
import ca.ulaval.glo4003.ultaxi.utils.hashing.BcryptHashing;
import ca.ulaval.glo4003.ultaxi.utils.hashing.HashingStrategy;
import jersey.repackaged.com.google.common.collect.Sets;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;
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
import java.util.List;
import java.util.Set;

public final class ULTaxiMain {

    private static final TokenManager tokenManager = new JWTTokenManager();
    private static final TokenRepository tokenRepository = new TokenRepositoryInMemory();
    private static final UserRepository userRepository = new UserRepositoryInMemory();
    private static final VehicleRepository vehicleRepository = new VehicleRepositoryInMemory();
    private static final String EMAIL_SENDER_CONFIGURATION_FILENAME = "emailSenderConfiguration.properties";
    private static final MessagingTaskQueue MESSAGING_TASK_QUEUE = new MessagingTaskQueueInMemory();
    private static boolean isDevelopmentEnvironment;
    private static int serverPort;

    private ULTaxiMain() {
        throw new AssertionError("Instantiating main class...");
    }

    public static void main(String[] args) throws Exception {

        parseCommandLineOptions(args);
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

        Thread messagingTaskConsumer = new Thread(new MessagingTaskConsumerImpl(MESSAGING_TASK_QUEUE));
        messagingTaskConsumer.start();

        ContextHandlerCollection contexts = new ContextHandlerCollection();
        contexts.setHandlers(new Handler[]{context});
        Server server = new Server(serverPort);
        server.setHandler(contexts);

        setDevelopmentEnvironmentMockData();

        try {
            server.start();
            server.join();
        } finally {
            server.destroy();
        }
    }

    private static void setDevelopmentEnvironmentMockData() {
        if (isDevelopmentEnvironment) {
            UserDevDataFactory userDevDataFactory = new UserDevDataFactory();
            List<User> users = userDevDataFactory.createMockData();
            users.forEach(userRepository::save);

            VehicleDevDataFactory vehicleDevDataFactory = new VehicleDevDataFactory();
            List<Vehicle> vehicles = vehicleDevDataFactory.createMockData();
            vehicles.forEach(vehicleRepository::save);
        }

    }

    private static void parseCommandLineOptions(String[] arguments) {
        CommandLineParser parser = new PosixParser();
        Options options = createCommandLineOptions();
        boolean displayHelp;
        try {
            CommandLine commandLine = parser.parse(options, arguments);
            assignCommandLineOptions(commandLine);
            displayHelp = commandLine.hasOption("help");
        } catch (ParseException | NumberFormatException exception) {
            displayHelp = true;
        }

        displayHelp(displayHelp, options);
    }

    private static void displayHelp(boolean displayHelp, Options options) {
        if (displayHelp) {
            HelpFormatter helpFormatter = new HelpFormatter();
            helpFormatter.printHelp("ultaxi", options);
            System.exit(1);
        }
    }

    private static void assignCommandLineOptions(CommandLine commandLine) {
        isDevelopmentEnvironment = commandLine.hasOption("development");
        serverPort = Integer.parseUnsignedInt(commandLine.getOptionValue("server-port", "8080"));
    }

    private static Options createCommandLineOptions() {
        Options options = new Options();
        options.addOption(createIsDevelopmentEnvironmentOption());
        options.addOption(createServerPortOption());
        options.addOption(createHelpOption());

        return options;
    }

    private static Option createIsDevelopmentEnvironmentOption() {
        return OptionBuilder
            .withLongOpt("development")
            .hasArg(false)
            .withDescription("run the server in development mode")
            .isRequired(false)
            .create("d");
    }

    private static Option createServerPortOption() {
        return OptionBuilder
            .withLongOpt("server-port")
            .withDescription("run the server on the specified port")
            .withArgName("PORT_NUMBER")
            .isRequired(false)
            .withType(Integer.class)
            .hasArgs(1)
            .create("p");
    }

    private static Option createHelpOption() {
        return OptionBuilder
            .withLongOpt("help")
            .withDescription("display this help text and exit")
            .isRequired(false)
            .hasArg(false)
            .create("h");
    }

    private static Set<Object> getContextResources() {
        EmailSender emailSender = createEmailSender();
        UserService userService = createUserService(emailSender);
        UserAuthenticationService userAuthenticationService = createUserAuthenticationService();
        VehicleService vehicleService = createVehicleService();
        DriverService driverService = createDriverService();

        UserResource userResource = createUserResource(userService);
        DriverResource driverResource = createDriverResource(driverService);
        VehicleResource vehicleResource = createVehicleResource(vehicleService);
        UserAuthenticationResource userAuthenticationResource = createUseAuthenticationResource(
            userAuthenticationService);
        TransportRequestResource transportRequestResource = createTransportRequestResource();

        return Collections.unmodifiableSet(Sets.newHashSet(driverResource,
                                                           userResource,
                                                           vehicleResource,
                                                           userAuthenticationResource,
                                                           transportRequestResource));
    }

    private static EmailSender createEmailSender() {
        EmailSenderConfigurationReader emailSenderConfigurationReader =
                new EmailSenderConfigurationPropertyFileReader(EMAIL_SENDER_CONFIGURATION_FILENAME);
        EmailSender emailSender = new EmailSender(emailSenderConfigurationReader);
        return emailSender;
    }

    private static UserService createUserService(EmailSender emailSender) {
        MessagingTaskProducer messagingTaskProducer = new MessagingTaskProducerImpl(MESSAGING_TASK_QUEUE);
        UserService userService = new UserService(userRepository, createUserAssembler(),
                messagingTaskProducer, emailSender);
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

        return new VehicleService(vehicleRepository, vehicleAssembler);
    }

    private static UserResource createUserResource(UserService userService) {
        return new UserResourceImpl(userService);
    }

    private static VehicleResource createVehicleResource(VehicleService vehicleService) {
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
        DriverValidator driverValidator = new DriverValidator(userRepository);
        return new DriverService(userRepository, driverAssembler, driverValidator);
    }
}
