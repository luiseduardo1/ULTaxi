package ca.ulaval.glo4003;

import ca.ulaval.glo4003.ws.api.user.UserResource;
import ca.ulaval.glo4003.ws.api.user.UserResourceImpl;
import ca.ulaval.glo4003.ws.domain.messaging.MessageProducerService;
import ca.ulaval.glo4003.ws.domain.messaging.MessageQueue;
import ca.ulaval.glo4003.ws.domain.user.User;
import ca.ulaval.glo4003.ws.domain.user.UserAssembler;
import ca.ulaval.glo4003.ws.domain.user.UserRepository;
import ca.ulaval.glo4003.ws.domain.user.UserService;
import ca.ulaval.glo4003.ws.http.CORSResponseFilter;
import ca.ulaval.glo4003.ws.infrastructure.messaging.EmailSender;
import ca.ulaval.glo4003.ws.infrastructure.messaging.EmailSenderConfigurationPropertyFileReader;
import ca.ulaval.glo4003.ws.infrastructure.messaging.EmailSenderConfigurationReader;
import ca.ulaval.glo4003.ws.infrastructure.messaging.MessageQueueInMemory;
import ca.ulaval.glo4003.ws.infrastructure.user.UserDevDataFactory;
import ca.ulaval.glo4003.ws.infrastructure.user.UserRepositoryInMemory;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * RESTApi setup without using DI or spring
 */
@SuppressWarnings("all")
public class ULTaxiMain {

    private static final int SERVER_PORT = 8081;
    private static boolean isDev = true; // Would be a JVM argument or in a .property file
    private static MessageQueue messageQueueInMemory = new MessageQueueInMemory();
    private static String EMAIL_SENDER_CONFIGURATION_FILENAME = "emailSenderConfiguration.properties";

    public static void main(String[] args) throws Exception {

        // Setup resources (API)
        UserResource userResource = createUserResource();

        // Setup API context (JERSEY + JETTY)
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/api/");
        ResourceConfig resourceConfig = ResourceConfig.forApplication(new Application() {
            @Override
            public Set<Object> getSingletons() {
                HashSet<Object> resources = new HashSet<>();
                // Add resources to context
                resources.add(userResource);
                return resources;
            }
        });
        resourceConfig.register(CORSResponseFilter.class);

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

    private static UserResource createUserResource() {
        // Setup resources' dependencies (DOMAIN + INFRASTRUCTURE)
        UserRepository userRepository = new UserRepositoryInMemory();

        // For development ease
        if (isDev) {
            UserDevDataFactory userDevDataFactory = new UserDevDataFactory();
            List<User> users = userDevDataFactory.createMockData();
            users.stream().forEach(userRepository::save);
        }

        UserAssembler userAssembler = new UserAssembler();
        MessageProducerService messageProducerService = new MessageProducerService(
            messageQueueInMemory);

        UserService userService = new UserService(userRepository, userAssembler,
                                                  messageProducerService);
        return new UserResourceImpl(userService);
    }
}
